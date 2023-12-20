//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.garcel.parser.r.autogen;


import java.io.IOException;
import java.io.PrintStream;

public class RTokenManager implements RConstants {
    private Token last = null;
    public PrintStream debugStream;
    static final long[] jjbitVec0 = new long[]{-2L, -1L, -1L, -1L};
    static final long[] jjbitVec2 = new long[]{0L, 0L, -1L, -1L};
    static final int[] jjnextStates = new int[]{44, 45, 47, 40, 50, 51, 53, 42, 66, 70, 74, 29, 31, 32, 24, 26, 27, 39, 40, 41, 42, 57, 40, 42, 36, 37, 44, 45, 47, 40, 46, 47, 40, 50, 51, 53, 42, 52, 53, 42, 66, 67, 40, 70, 71, 42, 48, 49, 54, 55, 68, 69, 72, 73};
    public static final String[] jjstrLiteralImages = new String[]{"", null, null, null, ";", "(", ")", "{", "}", "[", "[[", "]", "&", "&&", "/", "==", null, "^", ">=", ">", "?", "%/%", "%x%", null, "%*%", "-", "%%", "*", "!=", "!", "<=", null, "$", "<", "|", "||", "%o%", "+", null, ":", null, "~", "@", "break", ",", null, "function", "else", "for", "if", "in", "next", "::", ":::", "repeat", null, "while", null, null, null, null, null, null, null, null, null, "Inf", "NA", "NaN", "NULL", null, null, null, null, null, null, null, null, null, null};
    int curLexState;
    int defaultLexState;
    int jjnewStateCnt;
    int jjround;
    int jjmatchedPos;
    int jjmatchedKind;
    public static final String[] lexStateNames = new String[]{"DEFAULT"};
    static final long[] jjtoToken = new long[]{252166394760658929L, 1276L};
    static final long[] jjtoSkip = new long[]{14L, 0L};
    static final long[] jjtoSpecial = new long[]{14L, 0L};
    protected SimpleCharStream input_stream;
    private final int[] jjrounds;
    private final int[] jjstateSet;
    protected int curChar;

    void CommonTokenAction(Token token) {
        token.prev = this.last;
        this.last = token;
        switch(token.kind) {
            case 57:
            case 74:
                Token prev = token.prev;
                if (prev != null) {
                    Token specialToken = token.specialToken;
                    int prevKind = prev.kind;
                    if (specialToken == null && (prevKind == 74 || prevKind == 57)) {
                        throw new TokenMgrError("Bad identifier matched at line " + prev.beginLine + ", column " + prev.beginColumn, 0);
                    }
                }
            default:
        }
    }

    public void setDebugStream(PrintStream ds) {
        this.debugStream = ds;
    }

    private final int jjStopStringLiteralDfa_0(int pos, long active0, long active1) {
        switch(pos) {
            case 0:
                if ((active0 & 68809654272L) != 0L) {
                    return 62;
                } else if ((active0 & 9663676416L) != 0L) {
                    return 10;
                } else if ((active0 & 33554432L) != 0L) {
                    return 13;
                } else if ((active0 & 32768L) != 0L) {
                    this.jjmatchedKind = 16;
                    return -1;
                } else {
                    if ((active0 & 94514019523624960L) == 0L && (active1 & 60L) == 0L) {
                        if ((active0 & 13511348637925376L) != 0L) {
                            return 7;
                        }

                        return -1;
                    }

                    this.jjmatchedKind = 74;
                    return 34;
                }
            case 1:
                if ((active0 & 1688849860263936L) == 0L && (active1 & 8L) == 0L) {
                    if ((active0 & 92825169663361024L) == 0L && (active1 & 52L) == 0L) {
                        if ((active0 & 68742545408L) != 0L) {
                            return 64;
                        }

                        if ((active0 & 32768L) != 0L) {
                            if (this.jjmatchedPos == 0) {
                                this.jjmatchedKind = 16;
                                this.jjmatchedPos = 0;
                            }

                            return -1;
                        }

                        return -1;
                    }

                    if (this.jjmatchedPos != 1) {
                        this.jjmatchedKind = 74;
                        this.jjmatchedPos = 1;
                    }

                    return 34;
                }

                return 34;
            case 2:
                if ((active0 & 281474976710656L) == 0L && (active1 & 20L) == 0L) {
                    if ((active0 & 92543694686650368L) == 0L && (active1 & 32L) == 0L) {
                        return -1;
                    }

                    this.jjmatchedKind = 74;
                    this.jjmatchedPos = 2;
                    return 34;
                }

                return 34;
            case 3:
                if ((active0 & 2392537302040576L) == 0L && (active1 & 32L) == 0L) {
                    if ((active0 & 90151157384609792L) != 0L) {
                        this.jjmatchedKind = 74;
                        this.jjmatchedPos = 3;
                        return 34;
                    }

                    return -1;
                }

                return 34;
            case 4:
                if ((active0 & 72066390130950144L) != 0L) {
                    return 34;
                } else {
                    if ((active0 & 18084767253659648L) != 0L) {
                        this.jjmatchedKind = 74;
                        this.jjmatchedPos = 4;
                        return 34;
                    }

                    return -1;
                }
            case 5:
                if ((active0 & 18014398509481984L) != 0L) {
                    return 34;
                } else {
                    if ((active0 & 70368744177664L) != 0L) {
                        this.jjmatchedKind = 74;
                        this.jjmatchedPos = 5;
                        return 34;
                    }

                    return -1;
                }
            case 6:
                if ((active0 & 70368744177664L) != 0L) {
                    this.jjmatchedKind = 74;
                    this.jjmatchedPos = 6;
                    return 34;
                }

                return -1;
            default:
                return -1;
        }
    }

    private final int jjStartNfa_0(int pos, long active0, long active1) {
        return this.jjMoveNfa_0(this.jjStopStringLiteralDfa_0(pos, active0, active1), pos + 1);
    }

    private int jjStopAtPos(int pos, int kind) {
        this.jjmatchedKind = kind;
        this.jjmatchedPos = pos;
        return pos + 1;
    }

    private int jjMoveStringLiteralDfa0_0() {
        switch(this.curChar) {
            case 33:
                this.jjmatchedKind = 29;
                return this.jjMoveStringLiteralDfa1_0(268435456L, 0L);
            case 34:
            case 35:
            case 39:
            case 46:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 74:
            case 75:
            case 76:
            case 77:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 92:
            case 95:
            case 96:
            case 97:
            case 99:
            case 100:
            case 103:
            case 104:
            case 106:
            case 107:
            case 108:
            case 109:
            case 111:
            case 112:
            case 113:
            case 115:
            case 116:
            case 117:
            case 118:
            case 120:
            case 121:
            case 122:
            default:
                return this.jjMoveNfa_0(0, 0);
            case 36:
                return this.jjStopAtPos(0, 32);
            case 37:
                return this.jjMoveStringLiteralDfa1_0(68809654272L, 0L);
            case 38:
                this.jjmatchedKind = 12;
                return this.jjMoveStringLiteralDfa1_0(8192L, 0L);
            case 40:
                return this.jjStopAtPos(0, 5);
            case 41:
                return this.jjStopAtPos(0, 6);
            case 42:
                return this.jjStopAtPos(0, 27);
            case 43:
                return this.jjStopAtPos(0, 37);
            case 44:
                return this.jjStopAtPos(0, 44);
            case 45:
                return this.jjStartNfaWithStates_0(0, 25, 13);
            case 47:
                return this.jjStopAtPos(0, 14);
            case 58:
                this.jjmatchedKind = 39;
                return this.jjMoveStringLiteralDfa1_0(13510798882111488L, 0L);
            case 59:
                return this.jjStopAtPos(0, 4);
            case 60:
                this.jjmatchedKind = 33;
                return this.jjMoveStringLiteralDfa1_0(1073741824L, 0L);
            case 61:
                return this.jjMoveStringLiteralDfa1_0(32768L, 0L);
            case 62:
                this.jjmatchedKind = 19;
                return this.jjMoveStringLiteralDfa1_0(262144L, 0L);
            case 63:
                return this.jjStopAtPos(0, 20);
            case 64:
                return this.jjStopAtPos(0, 42);
            case 73:
                return this.jjMoveStringLiteralDfa1_0(0L, 4L);
            case 78:
                return this.jjMoveStringLiteralDfa1_0(0L, 56L);
            case 91:
                this.jjmatchedKind = 9;
                return this.jjMoveStringLiteralDfa1_0(1024L, 0L);
            case 93:
                return this.jjStopAtPos(0, 11);
            case 94:
                return this.jjStopAtPos(0, 17);
            case 98:
                return this.jjMoveStringLiteralDfa1_0(8796093022208L, 0L);
            case 101:
                return this.jjMoveStringLiteralDfa1_0(140737488355328L, 0L);
            case 102:
                return this.jjMoveStringLiteralDfa1_0(351843720888320L, 0L);
            case 105:
                return this.jjMoveStringLiteralDfa1_0(1688849860263936L, 0L);
            case 110:
                return this.jjMoveStringLiteralDfa1_0(2251799813685248L, 0L);
            case 114:
                return this.jjMoveStringLiteralDfa1_0(18014398509481984L, 0L);
            case 119:
                return this.jjMoveStringLiteralDfa1_0(72057594037927936L, 0L);
            case 123:
                return this.jjStopAtPos(0, 7);
            case 124:
                this.jjmatchedKind = 34;
                return this.jjMoveStringLiteralDfa1_0(34359738368L, 0L);
            case 125:
                return this.jjStopAtPos(0, 8);
            case 126:
                return this.jjStopAtPos(0, 41);
        }
    }

    private int jjMoveStringLiteralDfa1_0(long active0, long active1) {
        try {
            this.curChar = this.input_stream.readChar();
        } catch (IOException var6) {
            this.jjStopStringLiteralDfa_0(0, active0, active1);
            return 1;
        }

        switch(this.curChar) {
            case 37:
                if ((active0 & 67108864L) != 0L) {
                    return this.jjStopAtPos(1, 26);
                }
                break;
            case 38:
                if ((active0 & 8192L) != 0L) {
                    return this.jjStopAtPos(1, 13);
                }
            case 39:
            case 40:
            case 41:
            case 43:
            case 44:
            case 45:
            case 46:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 59:
            case 60:
            case 62:
            case 63:
            case 64:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 98:
            case 99:
            case 100:
            case 103:
            case 105:
            case 106:
            case 107:
            case 109:
            case 112:
            case 113:
            case 115:
            case 116:
            case 118:
            case 119:
            case 121:
            case 122:
            case 123:
            default:
                break;
            case 42:
                return this.jjMoveStringLiteralDfa2_0(active0, 16777216L, active1, 0L);
            case 47:
                return this.jjMoveStringLiteralDfa2_0(active0, 2097152L, active1, 0L);
            case 58:
                if ((active0 & 4503599627370496L) != 0L) {
                    this.jjmatchedKind = 52;
                    this.jjmatchedPos = 1;
                }

                return this.jjMoveStringLiteralDfa2_0(active0, 9007199254740992L, active1, 0L);
            case 61:
                if ((active0 & 32768L) != 0L) {
                    return this.jjStopAtPos(1, 15);
                }

                if ((active0 & 262144L) != 0L) {
                    return this.jjStopAtPos(1, 18);
                }

                if ((active0 & 268435456L) != 0L) {
                    return this.jjStopAtPos(1, 28);
                }

                if ((active0 & 1073741824L) != 0L) {
                    return this.jjStopAtPos(1, 30);
                }
                break;
            case 65:
                if ((active1 & 8L) != 0L) {
                    return this.jjStartNfaWithStates_0(1, 67, 34);
                }
                break;
            case 85:
                return this.jjMoveStringLiteralDfa2_0(active0, 0L, active1, 32L);
            case 91:
                if ((active0 & 1024L) != 0L) {
                    return this.jjStopAtPos(1, 10);
                }
                break;
            case 97:
                return this.jjMoveStringLiteralDfa2_0(active0, 0L, active1, 16L);
            case 101:
                return this.jjMoveStringLiteralDfa2_0(active0, 20266198323167232L, active1, 0L);
            case 102:
                if ((active0 & 562949953421312L) != 0L) {
                    return this.jjStartNfaWithStates_0(1, 49, 34);
                }
                break;
            case 104:
                return this.jjMoveStringLiteralDfa2_0(active0, 72057594037927936L, active1, 0L);
            case 108:
                return this.jjMoveStringLiteralDfa2_0(active0, 140737488355328L, active1, 0L);
            case 110:
                if ((active0 & 1125899906842624L) != 0L) {
                    return this.jjStartNfaWithStates_0(1, 50, 34);
                }

                return this.jjMoveStringLiteralDfa2_0(active0, 0L, active1, 4L);
            case 111:
                return this.jjMoveStringLiteralDfa2_0(active0, 281543696187392L, active1, 0L);
            case 114:
                return this.jjMoveStringLiteralDfa2_0(active0, 8796093022208L, active1, 0L);
            case 117:
                return this.jjMoveStringLiteralDfa2_0(active0, 70368744177664L, active1, 0L);
            case 120:
                return this.jjMoveStringLiteralDfa2_0(active0, 4194304L, active1, 0L);
            case 124:
                if ((active0 & 34359738368L) != 0L) {
                    return this.jjStopAtPos(1, 35);
                }
        }

        return this.jjStartNfa_0(0, active0, active1);
    }

    private int jjMoveStringLiteralDfa2_0(long old0, long active0, long old1, long active1) {
        if (((active0 &= old0) | (active1 &= old1)) == 0L) {
            return this.jjStartNfa_0(0, old0, old1);
        } else {
            try {
                this.curChar = this.input_stream.readChar();
            } catch (IOException var10) {
                this.jjStopStringLiteralDfa_0(1, active0, active1);
                return 2;
            }

            switch(this.curChar) {
                case 37:
                    if ((active0 & 2097152L) != 0L) {
                        return this.jjStopAtPos(2, 21);
                    }

                    if ((active0 & 4194304L) != 0L) {
                        return this.jjStopAtPos(2, 22);
                    }

                    if ((active0 & 16777216L) != 0L) {
                        return this.jjStopAtPos(2, 24);
                    }

                    if ((active0 & 68719476736L) != 0L) {
                        return this.jjStopAtPos(2, 36);
                    }
                    break;
                case 58:
                    if ((active0 & 9007199254740992L) != 0L) {
                        return this.jjStopAtPos(2, 53);
                    }
                    break;
                case 76:
                    return this.jjMoveStringLiteralDfa3_0(active0, 0L, active1, 32L);
                case 78:
                    if ((active1 & 16L) != 0L) {
                        return this.jjStartNfaWithStates_0(2, 68, 34);
                    }
                    break;
                case 101:
                    return this.jjMoveStringLiteralDfa3_0(active0, 8796093022208L, active1, 0L);
                case 102:
                    if ((active1 & 4L) != 0L) {
                        return this.jjStartNfaWithStates_0(2, 66, 34);
                    }
                    break;
                case 105:
                    return this.jjMoveStringLiteralDfa3_0(active0, 72057594037927936L, active1, 0L);
                case 110:
                    return this.jjMoveStringLiteralDfa3_0(active0, 70368744177664L, active1, 0L);
                case 112:
                    return this.jjMoveStringLiteralDfa3_0(active0, 18014398509481984L, active1, 0L);
                case 114:
                    if ((active0 & 281474976710656L) != 0L) {
                        return this.jjStartNfaWithStates_0(2, 48, 34);
                    }
                    break;
                case 115:
                    return this.jjMoveStringLiteralDfa3_0(active0, 140737488355328L, active1, 0L);
                case 120:
                    return this.jjMoveStringLiteralDfa3_0(active0, 2251799813685248L, active1, 0L);
            }

            return this.jjStartNfa_0(1, active0, active1);
        }
    }

    private int jjMoveStringLiteralDfa3_0(long old0, long active0, long old1, long active1) {
        if (((active0 &= old0) | (active1 &= old1)) == 0L) {
            return this.jjStartNfa_0(1, old0, old1);
        } else {
            try {
                this.curChar = this.input_stream.readChar();
            } catch (IOException var10) {
                this.jjStopStringLiteralDfa_0(2, active0, active1);
                return 3;
            }

            switch(this.curChar) {
                case 76:
                    if ((active1 & 32L) != 0L) {
                        return this.jjStartNfaWithStates_0(3, 69, 34);
                    }
                    break;
                case 97:
                    return this.jjMoveStringLiteralDfa4_0(active0, 8796093022208L, active1, 0L);
                case 99:
                    return this.jjMoveStringLiteralDfa4_0(active0, 70368744177664L, active1, 0L);
                case 101:
                    if ((active0 & 140737488355328L) != 0L) {
                        return this.jjStartNfaWithStates_0(3, 47, 34);
                    }

                    return this.jjMoveStringLiteralDfa4_0(active0, 18014398509481984L, active1, 0L);
                case 108:
                    return this.jjMoveStringLiteralDfa4_0(active0, 72057594037927936L, active1, 0L);
                case 116:
                    if ((active0 & 2251799813685248L) != 0L) {
                        return this.jjStartNfaWithStates_0(3, 51, 34);
                    }
            }

            return this.jjStartNfa_0(2, active0, active1);
        }
    }

    private int jjMoveStringLiteralDfa4_0(long old0, long active0, long old1, long active1) {
        if (((active0 &= old0) | active1 & old1) == 0L) {
            return this.jjStartNfa_0(2, old0, old1);
        } else {
            try {
                this.curChar = this.input_stream.readChar();
            } catch (IOException var10) {
                this.jjStopStringLiteralDfa_0(3, active0, 0L);
                return 4;
            }

            switch(this.curChar) {
                case 97:
                    return this.jjMoveStringLiteralDfa5_0(active0, 18014398509481984L);
                case 101:
                    if ((active0 & 72057594037927936L) != 0L) {
                        return this.jjStartNfaWithStates_0(4, 56, 34);
                    }
                    break;
                case 107:
                    if ((active0 & 8796093022208L) != 0L) {
                        return this.jjStartNfaWithStates_0(4, 43, 34);
                    }
                    break;
                case 116:
                    return this.jjMoveStringLiteralDfa5_0(active0, 70368744177664L);
            }

            return this.jjStartNfa_0(3, active0, 0L);
        }
    }

    private int jjMoveStringLiteralDfa5_0(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_0(3, old0, 0L);
        } else {
            try {
                this.curChar = this.input_stream.readChar();
            } catch (IOException var6) {
                this.jjStopStringLiteralDfa_0(4, active0, 0L);
                return 5;
            }

            switch(this.curChar) {
                case 105:
                    return this.jjMoveStringLiteralDfa6_0(active0, 70368744177664L);
                case 116:
                    if ((active0 & 18014398509481984L) != 0L) {
                        return this.jjStartNfaWithStates_0(5, 54, 34);
                    }
                default:
                    return this.jjStartNfa_0(4, active0, 0L);
            }
        }
    }

    private int jjMoveStringLiteralDfa6_0(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_0(4, old0, 0L);
        } else {
            try {
                this.curChar = this.input_stream.readChar();
            } catch (IOException var6) {
                this.jjStopStringLiteralDfa_0(5, active0, 0L);
                return 6;
            }

            switch(this.curChar) {
                case 111:
                    return this.jjMoveStringLiteralDfa7_0(active0, 70368744177664L);
                default:
                    return this.jjStartNfa_0(5, active0, 0L);
            }
        }
    }

    private int jjMoveStringLiteralDfa7_0(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_0(5, old0, 0L);
        } else {
            try {
                this.curChar = this.input_stream.readChar();
            } catch (IOException var6) {
                this.jjStopStringLiteralDfa_0(6, active0, 0L);
                return 7;
            }

            switch(this.curChar) {
                case 110:
                    if ((active0 & 70368744177664L) != 0L) {
                        return this.jjStartNfaWithStates_0(7, 46, 34);
                    }
                default:
                    return this.jjStartNfa_0(6, active0, 0L);
            }
        }
    }

    private int jjStartNfaWithStates_0(int pos, int kind, int state) {
        this.jjmatchedKind = kind;
        this.jjmatchedPos = pos;

        try {
            this.curChar = this.input_stream.readChar();
        } catch (IOException var5) {
            return pos + 1;
        }

        return this.jjMoveNfa_0(state, pos + 1);
    }

    private int jjMoveNfa_0(int startState, int curPos) {
        int startsAt = 0;
        this.jjnewStateCnt = 76;
        int i = 1;
        this.jjstateSet[0] = startState;
        int kind = 2147483647;

        while(true) {
            if (++this.jjround == 2147483647) {
                this.ReInitRounds();
            }

            long l;
            if (this.curChar < 64) {
                l = 1L << this.curChar;

                do {
                    --i;
                    switch(this.jjstateSet[i]) {
                        case 0:
                            if ((287948901175001088L & l) != 0L) {
                                if (kind > 57) {
                                    kind = 57;
                                }

                                this.jjCheckNAddStates(0, 7);
                            } else if ((4294971904L & l) != 0L) {
                                if (kind > 3) {
                                    kind = 3;
                                }

                                this.jjCheckNAdd(5);
                            } else if ((9216L & l) != 0L) {
                                if (kind > 2) {
                                    kind = 2;
                                }

                                this.jjCheckNAddTwoStates(2, 4);
                            } else if (this.curChar == 46) {
                                if (kind > 74) {
                                    kind = 74;
                                }

                                this.jjCheckNAddStates(8, 10);
                            } else if (this.curChar == 37) {
                                this.jjCheckNAddTwoStates(62, 63);
                            } else if (this.curChar == 34) {
                                this.jjCheckNAddStates(11, 13);
                            } else if (this.curChar == 39) {
                                this.jjCheckNAddStates(14, 16);
                            } else if (this.curChar == 45) {
                                this.jjstateSet[this.jjnewStateCnt++] = 13;
                            } else if (this.curChar == 60) {
                                this.jjCheckNAddTwoStates(10, 11);
                            } else if (this.curChar == 58) {
                                this.jjstateSet[this.jjnewStateCnt++] = 7;
                            } else if (this.curChar == 61) {
                                if (kind > 16) {
                                    kind = 16;
                                }
                            } else if (this.curChar == 35) {
                                if (kind > 1) {
                                    kind = 1;
                                }

                                this.jjCheckNAdd(1);
                            }

                            if ((287667426198290432L & l) != 0L) {
                                if (kind > 57) {
                                    kind = 57;
                                }

                                this.jjCheckNAddStates(17, 20);
                            } else if (this.curChar == 48) {
                                if (kind > 57) {
                                    kind = 57;
                                }

                                this.jjCheckNAddStates(21, 23);
                            } else if (this.curChar == 13) {
                                this.jjstateSet[this.jjnewStateCnt++] = 3;
                            }
                            break;
                        case 1:
                            if ((-9217L & l) != 0L) {
                                if (kind > 1) {
                                    kind = 1;
                                }

                                this.jjCheckNAdd(1);
                            }
                            break;
                        case 2:
                            if ((9216L & l) != 0L) {
                                if (kind > 2) {
                                    kind = 2;
                                }

                                this.jjCheckNAddTwoStates(2, 4);
                            }
                            break;
                        case 3:
                            if (this.curChar == 10) {
                                if (kind > 2) {
                                    kind = 2;
                                }

                                this.jjCheckNAddTwoStates(2, 4);
                            }
                            break;
                        case 4:
                            if (this.curChar == 13) {
                                this.jjstateSet[this.jjnewStateCnt++] = 3;
                            }
                            break;
                        case 5:
                            if ((4294971904L & l) != 0L) {
                                if (kind > 3) {
                                    kind = 3;
                                }

                                this.jjCheckNAdd(5);
                            }
                            break;
                        case 6:
                        case 7:
                            if (this.curChar == 61 && kind > 16) {
                                kind = 16;
                            }
                            break;
                        case 8:
                            if (this.curChar == 58) {
                                this.jjstateSet[this.jjnewStateCnt++] = 7;
                            }
                            break;
                        case 9:
                            if (this.curChar == 60) {
                                this.jjCheckNAddTwoStates(10, 11);
                            }
                            break;
                        case 10:
                            if (this.curChar == 45) {
                                if (kind > 31) {
                                    kind = 31;
                                }
                            } else if (this.curChar == 60) {
                                this.jjCheckNAdd(11);
                            }
                            break;
                        case 11:
                            if (this.curChar == 45 && kind > 31) {
                                kind = 31;
                            }
                            break;
                        case 12:
                            if (this.curChar == 45) {
                                this.jjstateSet[this.jjnewStateCnt++] = 13;
                            }
                            break;
                        case 13:
                            if (this.curChar == 62) {
                                if (kind > 38) {
                                    kind = 38;
                                }

                                this.jjstateSet[this.jjnewStateCnt++] = 14;
                            }
                            break;
                        case 14:
                            if (this.curChar == 62 && kind > 38) {
                                kind = 38;
                            }
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                        case 24:
                        case 29:
                        case 33:
                        case 35:
                        case 37:
                        case 40:
                        case 42:
                        case 47:
                        case 53:
                        case 57:
                        case 60:
                        case 67:
                        case 71:
                        default:
                            break;
                        case 23:
                            if (this.curChar == 39) {
                                this.jjCheckNAddStates(14, 16);
                            }
                            break;
                        case 25:
                            this.jjCheckNAddStates(14, 16);
                            break;
                        case 26:
                            if ((-549755813889L & l) != 0L) {
                                this.jjCheckNAddStates(14, 16);
                            }
                            break;
                        case 27:
                            if (this.curChar == 39 && kind > 71) {
                                kind = 71;
                            }
                            break;
                        case 28:
                            if (this.curChar == 34) {
                                this.jjCheckNAddStates(11, 13);
                            }
                            break;
                        case 30:
                            this.jjCheckNAddStates(11, 13);
                            break;
                        case 31:
                            if ((-17179869185L & l) != 0L) {
                                this.jjCheckNAddStates(11, 13);
                            }
                            break;
                        case 32:
                            if (this.curChar == 34 && kind > 71) {
                                kind = 71;
                            }
                            break;
                        case 34:
                            if ((288019269919178752L & l) != 0L) {
                                if (kind > 74) {
                                    kind = 74;
                                }

                                this.jjstateSet[this.jjnewStateCnt++] = 34;
                            }
                            break;
                        case 36:
                            this.jjAddStates(24, 25);
                            break;
                        case 38:
                            if ((287667426198290432L & l) != 0L) {
                                if (kind > 57) {
                                    kind = 57;
                                }

                                this.jjCheckNAddStates(17, 20);
                            }
                            break;
                        case 39:
                            if ((287948901175001088L & l) != 0L) {
                                if (kind > 57) {
                                    kind = 57;
                                }

                                this.jjCheckNAddTwoStates(39, 40);
                            }
                            break;
                        case 41:
                            if ((287948901175001088L & l) != 0L) {
                                this.jjCheckNAddTwoStates(41, 42);
                            }
                            break;
                        case 43:
                            if ((287948901175001088L & l) != 0L) {
                                if (kind > 57) {
                                    kind = 57;
                                }

                                this.jjCheckNAddStates(0, 7);
                            }
                            break;
                        case 44:
                            if ((287948901175001088L & l) != 0L) {
                                if (kind > 57) {
                                    kind = 57;
                                }

                                this.jjCheckNAddStates(26, 29);
                            }
                            break;
                        case 45:
                            if (this.curChar == 46) {
                                if (kind > 57) {
                                    kind = 57;
                                }

                                this.jjCheckNAddStates(30, 32);
                            }
                            break;
                        case 46:
                            if ((287948901175001088L & l) != 0L) {
                                if (kind > 57) {
                                    kind = 57;
                                }

                                this.jjCheckNAddStates(30, 32);
                            }
                            break;
                        case 48:
                            if ((43980465111040L & l) != 0L) {
                                this.jjCheckNAdd(49);
                            }
                            break;
                        case 49:
                            if ((287948901175001088L & l) != 0L) {
                                if (kind > 57) {
                                    kind = 57;
                                }

                                this.jjCheckNAddTwoStates(49, 40);
                            }
                            break;
                        case 50:
                            if ((287948901175001088L & l) != 0L) {
                                this.jjCheckNAddStates(33, 36);
                            }
                            break;
                        case 51:
                            if (this.curChar == 46) {
                                this.jjCheckNAddStates(37, 39);
                            }
                            break;
                        case 52:
                            if ((287948901175001088L & l) != 0L) {
                                this.jjCheckNAddStates(37, 39);
                            }
                            break;
                        case 54:
                            if ((43980465111040L & l) != 0L) {
                                this.jjCheckNAdd(55);
                            }
                            break;
                        case 55:
                            if ((287948901175001088L & l) != 0L) {
                                this.jjCheckNAddTwoStates(55, 42);
                            }
                            break;
                        case 56:
                            if (this.curChar == 48) {
                                if (kind > 57) {
                                    kind = 57;
                                }

                                this.jjCheckNAddStates(21, 23);
                            }
                            break;
                        case 58:
                            if ((287948901175001088L & l) != 0L) {
                                if (kind > 57) {
                                    kind = 57;
                                }

                                this.jjCheckNAddTwoStates(58, 40);
                            }
                            break;
                        case 59:
                            if (this.curChar == 37) {
                                this.jjCheckNAddTwoStates(62, 63);
                            }
                            break;
                        case 61:
                            if (this.curChar == 37 && kind > 23) {
                                kind = 23;
                            }
                            break;
                        case 62:
                        case 63:
                            if ((-137438953473L & l) != 0L) {
                                this.jjCheckNAddTwoStates(63, 64);
                            }
                            break;
                        case 64:
                            if ((-137438953473L & l) != 0L) {
                                this.jjCheckNAddTwoStates(63, 64);
                            } else if (this.curChar == 37 && kind > 40) {
                                kind = 40;
                            }
                            break;
                        case 65:
                            if (this.curChar == 46) {
                                if (kind > 74) {
                                    kind = 74;
                                }

                                this.jjCheckNAddStates(8, 10);
                            }
                            break;
                        case 66:
                            if ((287948901175001088L & l) != 0L) {
                                if (kind > 57) {
                                    kind = 57;
                                }

                                this.jjCheckNAddStates(40, 42);
                            }
                            break;
                        case 68:
                            if ((43980465111040L & l) != 0L) {
                                this.jjCheckNAdd(69);
                            }
                            break;
                        case 69:
                            if ((287948901175001088L & l) != 0L) {
                                if (kind > 57) {
                                    kind = 57;
                                }

                                this.jjCheckNAddTwoStates(69, 40);
                            }
                            break;
                        case 70:
                            if ((287948901175001088L & l) != 0L) {
                                this.jjCheckNAddStates(43, 45);
                            }
                            break;
                        case 72:
                            if ((43980465111040L & l) != 0L) {
                                this.jjCheckNAdd(73);
                            }
                            break;
                        case 73:
                            if ((287948901175001088L & l) != 0L) {
                                this.jjCheckNAddTwoStates(73, 42);
                            }
                            break;
                        case 74:
                            if (this.curChar == 46) {
                                if (kind > 74) {
                                    kind = 74;
                                }

                                this.jjCheckNAdd(75);
                            }
                            break;
                        case 75:
                            if ((288019269919178752L & l) != 0L) {
                                if (kind > 74) {
                                    kind = 74;
                                }

                                this.jjCheckNAdd(75);
                            }
                    }
                } while(i != startsAt);
            } else if (this.curChar < 128) {
                l = 1L << (this.curChar & 63);

                do {
                    --i;
                    switch(this.jjstateSet[i]) {
                        case 0:
                            if ((576460743847706622L & l) != 0L) {
                                if (kind > 74) {
                                    kind = 74;
                                }

                                this.jjCheckNAdd(34);
                            } else if (this.curChar == 96) {
                                this.jjCheckNAddTwoStates(36, 37);
                            }

                            if (this.curChar == 70) {
                                this.jjstateSet[this.jjnewStateCnt++] = 21;
                            } else if (this.curChar == 84) {
                                this.jjstateSet[this.jjnewStateCnt++] = 17;
                            }
                            break;
                        case 1:
                            if (kind > 1) {
                                kind = 1;
                            }

                            this.jjstateSet[this.jjnewStateCnt++] = 1;
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                        case 14:
                        case 23:
                        case 27:
                        case 28:
                        case 32:
                        case 38:
                        case 39:
                        case 41:
                        case 43:
                        case 44:
                        case 45:
                        case 46:
                        case 48:
                        case 49:
                        case 50:
                        case 51:
                        case 52:
                        case 54:
                        case 55:
                        case 56:
                        case 59:
                        case 61:
                        case 65:
                        case 66:
                        case 68:
                        case 69:
                        case 70:
                        case 72:
                        case 73:
                        default:
                            break;
                        case 15:
                            if (this.curChar == 69 && kind > 70) {
                                kind = 70;
                            }
                            break;
                        case 16:
                            if (this.curChar == 85) {
                                this.jjCheckNAdd(15);
                            }
                            break;
                        case 17:
                            if (this.curChar == 82) {
                                this.jjstateSet[this.jjnewStateCnt++] = 16;
                            }
                            break;
                        case 18:
                            if (this.curChar == 84) {
                                this.jjstateSet[this.jjnewStateCnt++] = 17;
                            }
                            break;
                        case 19:
                            if (this.curChar == 83) {
                                this.jjCheckNAdd(15);
                            }
                            break;
                        case 20:
                            if (this.curChar == 76) {
                                this.jjstateSet[this.jjnewStateCnt++] = 19;
                            }
                            break;
                        case 21:
                            if (this.curChar == 65) {
                                this.jjstateSet[this.jjnewStateCnt++] = 20;
                            }
                            break;
                        case 22:
                            if (this.curChar == 70) {
                                this.jjstateSet[this.jjnewStateCnt++] = 21;
                            }
                            break;
                        case 24:
                            if (this.curChar == 92) {
                                this.jjstateSet[this.jjnewStateCnt++] = 25;
                            }
                            break;
                        case 25:
                            this.jjCheckNAddStates(14, 16);
                            break;
                        case 26:
                            if ((-268435457L & l) != 0L) {
                                this.jjCheckNAddStates(14, 16);
                            }
                            break;
                        case 29:
                            if (this.curChar == 92) {
                                this.jjstateSet[this.jjnewStateCnt++] = 30;
                            }
                            break;
                        case 30:
                            this.jjCheckNAddStates(11, 13);
                            break;
                        case 31:
                            if ((-268435457L & l) != 0L) {
                                this.jjCheckNAddStates(11, 13);
                            }
                            break;
                        case 33:
                            if ((576460743847706622L & l) != 0L) {
                                if (kind > 74) {
                                    kind = 74;
                                }

                                this.jjCheckNAdd(34);
                            }
                            break;
                        case 34:
                            if ((576460745995190270L & l) != 0L) {
                                if (kind > 74) {
                                    kind = 74;
                                }

                                this.jjCheckNAdd(34);
                            }
                            break;
                        case 35:
                            if (this.curChar == 96) {
                                this.jjCheckNAddTwoStates(36, 37);
                            }
                            break;
                        case 36:
                            if ((-4294967297L & l) != 0L) {
                                this.jjCheckNAddTwoStates(36, 37);
                            }
                            break;
                        case 37:
                            if (this.curChar == 96 && kind > 74) {
                                kind = 74;
                            }
                            break;
                        case 40:
                            if ((17592186048512L & l) != 0L && kind > 57) {
                                kind = 57;
                            }
                            break;
                        case 42:
                            if (this.curChar == 105 && kind > 57) {
                                kind = 57;
                            }
                            break;
                        case 47:
                            if ((137438953504L & l) != 0L) {
                                this.jjAddStates(46, 47);
                            }
                            break;
                        case 53:
                            if ((137438953504L & l) != 0L) {
                                this.jjAddStates(48, 49);
                            }
                            break;
                        case 57:
                            if ((72057594054705152L & l) != 0L) {
                                this.jjCheckNAdd(58);
                            }
                            break;
                        case 58:
                            if ((541165879422L & l) != 0L) {
                                if (kind > 57) {
                                    kind = 57;
                                }

                                this.jjCheckNAddTwoStates(58, 40);
                            }
                            break;
                        case 60:
                            if (this.curChar == 110) {
                                this.jjstateSet[this.jjnewStateCnt++] = 61;
                            }
                            break;
                        case 62:
                            this.jjCheckNAddTwoStates(63, 64);
                            if (this.curChar == 105) {
                                this.jjstateSet[this.jjnewStateCnt++] = 60;
                            }
                            break;
                        case 63:
                        case 64:
                            this.jjCheckNAddTwoStates(63, 64);
                            break;
                        case 67:
                            if ((137438953504L & l) != 0L) {
                                this.jjAddStates(50, 51);
                            }
                            break;
                        case 71:
                            if ((137438953504L & l) != 0L) {
                                this.jjAddStates(52, 53);
                            }
                            break;
                        case 74:
                        case 75:
                            if ((576460745995190270L & l) != 0L) {
                                if (kind > 74) {
                                    kind = 74;
                                }

                                this.jjCheckNAdd(75);
                            }
                    }
                } while(i != startsAt);
            } else {
                int hiByte = this.curChar >> 8;
                int i1 = hiByte >> 6;
                long l1 = 1L << (hiByte & 63);
                int i2 = (this.curChar & 255) >> 6;
                long l2 = 1L << (this.curChar & 63);

                do {
                    --i;
                    switch(this.jjstateSet[i]) {
                        case 1:
                            if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                                if (kind > 1) {
                                    kind = 1;
                                }

                                this.jjstateSet[this.jjnewStateCnt++] = 1;
                            }
                            break;
                        case 25:
                        case 26:
                            if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                                this.jjCheckNAddStates(14, 16);
                            }
                            break;
                        case 30:
                        case 31:
                            if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                                this.jjCheckNAddStates(11, 13);
                            }
                            break;
                        case 36:
                            if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                                this.jjAddStates(24, 25);
                            }
                            break;
                        case 62:
                        case 63:
                            if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                                this.jjCheckNAddTwoStates(63, 64);
                            }
                            break;
                        case 64:
                            if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                                this.jjCheckNAddTwoStates(63, 64);
                            }
                            break;
                        default:
                            if (i1 != 0 && l1 != 0L && i2 != 0 && l2 == 0L) {
                            }
                    }
                } while(i != startsAt);
            }

            if (kind != 2147483647) {
                this.jjmatchedKind = kind;
                this.jjmatchedPos = curPos;
                kind = 2147483647;
            }

            ++curPos;
            if ((i = this.jjnewStateCnt) == (startsAt = 76 - (this.jjnewStateCnt = startsAt))) {
                return curPos;
            }

            try {
                this.curChar = this.input_stream.readChar();
            } catch (IOException var13) {
                return curPos;
            }
        }
    }

    private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2) {
        switch(hiByte) {
            case 0:
                return (jjbitVec2[i2] & l2) != 0L;
            default:
                return (jjbitVec0[i1] & l1) != 0L;
        }
    }

    protected Token jjFillToken() {
        String im = jjstrLiteralImages[this.jjmatchedKind];
        String curTokenImage = im == null ? this.input_stream.GetImage() : im;
        int beginLine = this.input_stream.getBeginLine();
        int beginColumn = this.input_stream.getBeginColumn();
        int endLine = this.input_stream.getEndLine();
        int endColumn = this.input_stream.getEndColumn();
        Token t = Token.newToken(this.jjmatchedKind, curTokenImage);
        t.beginLine = beginLine;
        t.endLine = endLine;
        t.beginColumn = beginColumn;
        t.endColumn = endColumn;
        return t;
    }

    public Token getNextToken() {
        Token specialToken = null;
        boolean var3 = false;

        while(true) {
            Token matchedToken;
            try {
                this.curChar = this.input_stream.BeginToken();
            } catch (Exception var9) {
                this.jjmatchedKind = 0;
                this.jjmatchedPos = -1;
                matchedToken = this.jjFillToken();

                matchedToken.specialToken = specialToken;
                this.CommonTokenAction(matchedToken);
                return matchedToken;
            }

            this.jjmatchedKind = 2147483647;
            this.jjmatchedPos = 0;
            int curPos = this.jjMoveStringLiteralDfa0_0();
            if (this.jjmatchedKind == 2147483647) {
                int error_line = this.input_stream.getEndLine();
                int error_column = this.input_stream.getEndColumn();
                String error_after = null;
                boolean EOFSeen = false;

                try {
                    this.input_stream.readChar();
                    this.input_stream.backup(1);
                } catch (IOException var10) {
                    EOFSeen = true;
                    error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
                    if (this.curChar != 10 && this.curChar != 13) {
                        ++error_column;
                    } else {
                        ++error_line;
                        error_column = 0;
                    }
                }

                if (!EOFSeen) {
                    this.input_stream.backup(1);
                    error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
                }

                throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
            }

            if (this.jjmatchedPos + 1 < curPos) {
                this.input_stream.backup(curPos - this.jjmatchedPos - 1);
            }

            if ((jjtoToken[this.jjmatchedKind >> 6] & 1L << (this.jjmatchedKind & 63)) != 0L) {
                matchedToken = this.jjFillToken();

                matchedToken.specialToken = specialToken;
                this.CommonTokenAction(matchedToken);
                return matchedToken;
            }

            if ((jjtoSpecial[this.jjmatchedKind >> 6] & 1L << (this.jjmatchedKind & 63)) != 0L) {
                matchedToken = this.jjFillToken();
                if (specialToken == null) {
                    specialToken = matchedToken;
                } else {
                    matchedToken.specialToken = specialToken;
                    specialToken = specialToken.next = matchedToken;
                }
            }
        }
    }

    private void jjCheckNAdd(int state) {
        if (this.jjrounds[state] != this.jjround) {
            this.jjstateSet[this.jjnewStateCnt++] = state;
            this.jjrounds[state] = this.jjround;
        }

    }

    private void jjAddStates(int start, int end) {
        do {
            this.jjstateSet[this.jjnewStateCnt++] = jjnextStates[start];
        } while(start++ != end);

    }

    private void jjCheckNAddTwoStates(int state1, int state2) {
        this.jjCheckNAdd(state1);
        this.jjCheckNAdd(state2);
    }

    private void jjCheckNAddStates(int start, int end) {
        do {
            this.jjCheckNAdd(jjnextStates[start]);
        } while(start++ != end);

    }

    public RTokenManager(SimpleCharStream stream) {
        this.debugStream = System.out;
        this.curLexState = 0;
        this.defaultLexState = 0;
        this.jjrounds = new int[76];
        this.jjstateSet = new int[152];
        this.input_stream = stream;
    }

    public RTokenManager(SimpleCharStream stream, int lexState) {
        this.debugStream = System.out;
        this.curLexState = 0;
        this.defaultLexState = 0;
        this.jjrounds = new int[76];
        this.jjstateSet = new int[152];
        this.ReInit(stream);
        this.SwitchTo(lexState);
    }

    public void ReInit(SimpleCharStream stream) {
        this.jjmatchedPos = this.jjnewStateCnt = 0;
        this.curLexState = this.defaultLexState;
        this.input_stream = stream;
        this.ReInitRounds();
    }

    private void ReInitRounds() {
        this.jjround = -2147483647;

        for(int i = 76; i-- > 0; this.jjrounds[i] = -2147483648) {
        }

    }

    public void ReInit(SimpleCharStream stream, int lexState) {
        this.ReInit(stream);
        this.SwitchTo(lexState);
    }

    public void SwitchTo(int lexState) {
        if (lexState < 1 && lexState >= 0) {
            this.curLexState = lexState;
        } else {
            throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
        }
    }
}

