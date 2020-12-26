I wrote a simple program to simulate the bridge-processing flowchart for
a bridge that we discussed in the lectures. 

I was given a text file (BridgeFDB.txt) that contains the
forwarding database of a bridge with four ports.

00-00-00-11-0b-0d --> MAC address of the destination host
1 	 	  --> Port number
00-13-46-c6-a5-35
2


I am also given (RandomFrames.txt) that contains the source MAC address, the destination
MAC address of a random frame on the network that the bridge is connected to, and the port number of the
bridge that it arrives on.

00-00-00-11-0b-0d --> Source MAC address
00-13-46-c6-a5-35 --> Destination MAC address
1                 --> Arrival port number

The program can  read the two text files, and for each random frame, it should make a decision either to
forward/discard/broadcast the frame. If the source MAC address is not in the database, then it should update
it.