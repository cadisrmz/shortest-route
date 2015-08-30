# Small application to compute all routes from Source to Destination given a directed graph

##Input:
Data file with multiple lines of the format:
SRC DEST 

Where each line represents an edge on a directed graph

## Expected output:
For a given pair of SRC and DEST, find all paths.

## Contraints
- No UI, other than CLI
- The input to the algorithm is in the form of a text file that will contain all possible connections, as a directed graph. On each line, there will be a source and destination.
- Given that the only information required is the number of hops from source to destination, ignore the requirement of reading the number of flights that depart from each airport. Also, given that there is no time element involved, the number of flights plays no role.
- The command line will mention the source and the destination so that the paths can be computed
- There is need to have unit tests for the algorithm and the file parsing pieces.
