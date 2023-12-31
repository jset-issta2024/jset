package parser;

import lexer.*;
import node.*;
import analysis.*;
import ca.mcgill.sable.util.*;

import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

public class Parser
{
    public final Analysis ignoredTokens = new AnalysisAdapter();

    protected Node node;

    private final Lexer lexer;
    private final ListIterator stack = new LinkedList().listIterator();
    private int last_shift;
    private int last_pos;
    private int last_line;
    private final TokenIndex converter = new TokenIndex();
    private final int[] action = new int[2];

    private final static int SHIFT = 0;
    private final static int REDUCE = 1;
    private final static int ACCEPT = 2;
    private final static int ERROR = 3;

    protected void filter() throws ParserException, LexerException, IOException
    {
    }

    public Parser(Lexer lexer)
    {
        this.lexer = lexer;

        if(actionTable == null)
        {
            try
            {
                DataInputStream s = new DataInputStream(new BufferedInputStream(getClass().getResourceAsStream("parser.dat")));

                // read actionTable
                int length = s.readInt();
                actionTable = new int[length][][];
                for(int i = 0; i < actionTable.length; i++)
                {
                    length = s.readInt();
                    actionTable[i] = new int[length][3];
                    for(int j = 0; j < actionTable[i].length; j++)
                    {
                        for(int k = 0; k < 3; k++)
                        {
                            actionTable[i][j][k] = s.readInt();
                        }
                    }
                }

                // read gotoTable
                length = s.readInt();
                gotoTable = new int[length][][];
                for(int i = 0; i < gotoTable.length; i++)
                {
                    length = s.readInt();
                    gotoTable[i] = new int[length][2];
                    for(int j = 0; j < gotoTable[i].length; j++)
                    {
                        for(int k = 0; k < 2; k++)
                        {
                            gotoTable[i][j][k] = s.readInt();
                        }
                    }
                }

                // read errorMessages
                length = s.readInt();
                errorMessages = new String[length];
                for(int i = 0; i < errorMessages.length; i++)
                {
                    length = s.readInt();
                    StringBuffer buffer = new StringBuffer();

                    for(int j = 0; j < length; j++)
                    {
                        buffer.append(s.readChar());
                    }
                    errorMessages[i] = buffer.toString();
                }

                // read errors
                length = s.readInt();
                errors = new int[length];
                for(int i = 0; i < errors.length; i++)
                {
                    errors[i] = s.readInt();
                }

                s.close();
            }
            catch(Exception e)
            {
                throw new RuntimeException("Unable to read parser.dat.");
            }
        }
    }

    private int goTo(int index)
    {
        int state = state();
        int low = 1;
        int high = gotoTable[index].length - 1;
        int value = gotoTable[index][0][1];

        while(low <= high)
        {
            int middle = (low + high) / 2;

            if(state < gotoTable[index][middle][0])
            {
                high = middle - 1;
            }
            else if(state > gotoTable[index][middle][0])
            {
                low = middle + 1;
            }
            else
            {
                value = gotoTable[index][middle][1];
                break;
            }
        }

        return value;
    }

    private void push(int state, Node node, boolean filter) throws ParserException, LexerException, IOException
    {
        this.node = node;

        if(filter)
        {
            filter();
        }

        if(!stack.hasNext())
        {
            stack.add(new State(state, this.node));
            stack.next();
            return;
        }

        State s = (State) stack.next();
        s.state = state;
        s.node = this.node;
    }

    private int state()
    {
        State s = (State) stack.previous();
        stack.next();
        return s.state;
    }

    private Node pop()
    {
        return (Node) ((State) stack.previous()).node;
    }

    private int index(Switchable token)
    {
        converter.index = -1;
        token.apply(converter);
        return converter.index;
    }

    public Start parse() throws ParserException, LexerException, IOException
    {
        push(0, null, false);

        List ign = null;
        while(true)
        {
            while(index(lexer.peek()) == -1)
            {
                if(ign == null)
                {
                    ign = new TypedLinkedList(NodeCast.instance);
                }

                ign.add(lexer.next());
            }

            if(ign != null)
            {
                ignoredTokens.setIn(lexer.peek(), ign);
                ign = null;
            }

            last_pos = lexer.peek().getPos();
            last_line = lexer.peek().getLine();

            int index = index(lexer.peek());
            action[0] = actionTable[state()][0][1];
            action[1] = actionTable[state()][0][2];

            int low = 1;
            int high = actionTable[state()].length - 1;

            while(low <= high)
            {
                int middle = (low + high) / 2;

                if(index < actionTable[state()][middle][0])
                {
                    high = middle - 1;
                }
                else if(index > actionTable[state()][middle][0])
                {
                    low = middle + 1;
                }
                else
                {
                    action[0] = actionTable[state()][middle][1];
                    action[1] = actionTable[state()][middle][2];
                    break;
                }
            }

            switch(action[0])
            {
                case SHIFT:
                    push(action[1], lexer.next(), true);
                    last_shift = action[1];
                    break;
                case REDUCE:
                    switch(action[1])
                    {
                    case 0: { Node node = new0(); push(goTo(0), node, true); } break;
                    }
                    break;
                case ACCEPT:
                    {
                        EOF node2 = (EOF) lexer.next();
                        PExpression node1 = (PExpression) pop();
                        Start node = new Start(node1, node2);
                        return node;
                    }
                case ERROR:
                    throw new ParserException(
                        "[" + last_line + "," + last_pos + "] " +
                        errorMessages[errors[action[1]]]);
            }
        }
    }

    Node new0()
    {
        TIdentifier node1 = (TIdentifier) pop();
        AIdentifierExpression node = new AIdentifierExpression(node1);
        return node;
    }

    private static int[][][] actionTable;
/*      {
			{{-1, ERROR, 0}, {0, SHIFT, 1}, },
			{{-1, REDUCE, 0}, },
			{{-1, ERROR, 2}, {1, ACCEPT, -1}, },
        };*/
    private static int[][][] gotoTable;
/*      {
			{{-1, 2}, },
        };*/
    private static String[] errorMessages;
/*      {
			"TIdentifier expected.",
			"EOF expected.",
        };*/
    private static int[] errors;
/*      {
			0, 1, 1, 
        };*/
}
