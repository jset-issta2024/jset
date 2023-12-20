#!/usr/bin/python3

import os
import csv

if __name__ == "__main__":
    inputDir = os.path.dirname(__file__)
    for file in os.listdir( inputDir ):
        if ("csv" in file):
            file = os.path.join( inputDir, file )
            with open(file, 'r') as f:
                csvList = list(csv.reader(f))
                if (
                int(csvList[-1][-1]) <= 900 
                ):
                    for i,csvElm in enumerate(csvList):
                        if (i>0):
                            csvList[i][-1] = str( int(csvElm[-1]) * 1000 )
                    # csvList = [ str( int(csvElm[-1]) * 1000 )
                    #      for csvElm in csvList if (csvElm[0] != "branch")]
            with open(file, 'w') as f:
                csv_writer = csv.writer(f,dialect='excel')
                csv_writer.writerows(csvList)