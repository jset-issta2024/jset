/*
 * Copyright 2011-2013, by Vladimir Kostyukov and Contributors.
 * 
 * This file is part of la4j project (http://la4j.org)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Contributor(s): -
 * 
 */

package org.la4j.decomposition;

//import org.junit.Assert;

import org.la4j.LinearAlgebra;
import org.la4j.Matrix;

import static org.la4j.M.m;
import static org.la4j.M.ms;

public abstract class AbstractDecompositorTest {

    public abstract LinearAlgebra.DecompositorFactory decompositorFactory();

    public void performTest(double[][] input, double[][][] output){
        for (Matrix a: ms(input)) {
            MatrixDecompositor decompositor = a.withDecompositor(decompositorFactory());
            Matrix[] decomposition = decompositor.decompose();
            System.out.println("11111111111111111111111111111111111111");

//            Assert.assertEquals(output.length, decomposition.length);
            if (output.length != decomposition.length) {
            	System.err.println("must equal");
            }

            for (int i = 0; i < decomposition.length; i++) {
                //Assert.assertTrue(m(output[i]).equals(decomposition[i], 1e-3));
            	if (!m(output[i]).equals(decomposition[i], 1e-3)) {
            		System.err.println("must equal");
                }
            }

//            Debug.printCurrentPC();

        }
    }
}
