package parser;

final class State
{
    int state;
    Object node;

    State(int state, Object node)
    {
        this.state = state;
        this.node = node;
    }
}
