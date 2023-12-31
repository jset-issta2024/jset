/*
 *  Copyright  2020 Mark Slater
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package argo.saj;

/**
 * Thrown to indicate a given character stream is not valid JSON.
 */
public final class InvalidSyntaxException extends Exception {

    private final int column;
    private final int row;

    public InvalidSyntaxException(final String s, final int row, final int column) {
        super("At line " + row + ", column " + column + ":  " + s);
        this.column = column;
        this.row = row;
    }

    public InvalidSyntaxException(final String s, final Throwable throwable, final int row, final int column) {
        super("At line " + row + ", column " + column + ":  " + s, throwable);
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getLine() {
        return row;
    }
}
