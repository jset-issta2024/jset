#!/usr/bin/python3

import os
from multiprocessing import Pool

parameters = [
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestSweepSolver', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestSquareRootSolver', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestSingularValueDecompositor', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestSeidelSolver', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestQRDecompositor', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestNoPivotGaussInverter', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestLUDecompositor', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestLIA4J', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestLeastSquaresSolver', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestJacobiSolver', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestGaussJordanInverter', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestGaussianSolver', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestForwardBackSubstitutionSolver', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestEigenDecompositor', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-la4j', 'test.TestCholeskyDecompositor', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt', 'test.TestColtTVS', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt', 'test.TestColtSVD', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt', 'test.TestColtSolver', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt', 'test.TestColtRank', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt', 'test.TestColtQR', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt', 'test.TestColtLU', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt', 'test.TestColtED', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt', 'test.TestColtCD', '0', '0', '1'],
    ['/home/lmx/Documents/GitHub/jpf-concolic/jpf-concolic/build/example-colt', 'test.TestColt', '0', '0', '1']
]

def main():
    os.system("ulimit -s unlimited")
    pool = Pool(processes=4)
    cmd = "./6runTaintFloat.sh {} {} {} {} {}"

    for i in range(len(parameters)):
        pool.apply_async(os.system, args=(cmd.format(parameters[i][0],parameters[i][1],parameters[i][2],parameters[i][3],parameters[i][4]),))
        print(cmd.format(parameters[i][0],parameters[i][1],parameters[i][2],parameters[i][3],parameters[i][4]))
    pool.close()
    pool.join()

main()
