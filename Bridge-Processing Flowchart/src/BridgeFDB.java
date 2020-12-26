import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class BridgeFDB {


    public static void main(String[] args) throws IOException {
        //Two arrays to store host and port number from BridgesFDB
        ArrayList<String> BridgesDestination = new ArrayList<String>(46);
        ArrayList<Integer> BridgesHost = new ArrayList<Integer>(46);


        File BridgeInput= new File( "BridgeFDB.txt" );
        Scanner kb = new Scanner(BridgeInput);

        int i=0;            //counter to separate host and port
        while(kb.hasNextLine()){
         String temp =kb.nextLine();
         if(i%2==0){                                      //even i means it is destination MAC address
             BridgesDestination.add( temp );
         }else{
             BridgesHost.add( Integer.parseInt( temp ));  //odd is host number
         }
         i++;                                              //increment i
        }

        //for(int a=0;a<BridgesDestination.size(); a++){
        //    System.out.println(a+ ") "+BridgesDestination.get( a )+"\n");
        //}

        //Variables Arraylists to store random frames
        ArrayList<String> FramesSource = new ArrayList<String>();
        ArrayList<String> FramesDestination = new ArrayList<String>();
        ArrayList<Integer> FramesPort = new ArrayList<Integer>();

        File FrameInput= new File( "RandomFrames.txt" );
        kb = new Scanner(FrameInput);

        int b=0;

        while(kb.hasNextLine()){
            String tempo = kb.nextLine();

            if(b==0){
                FramesSource.add( tempo );
            }else if(b==1){
                FramesDestination.add(tempo);
            }else{
                FramesPort.add(Integer.parseInt( tempo ));
            }
            b++;
            if(b==3){b=0;}

        }
        //for(int a=0;a<FramesPort.size(); a++){
          //  System.out.println(a+ ") "+FramesPort.get( a )+"\n");
       // }
        
        //run through Frames list and update FDB
//        for(int c=0; c<FramesPort.size();c++){
//            if(BridgesDestination.contains( FramesSource.get(c) )){               //if it contains source
//                int index = BridgesDestination.indexOf( FramesSource.get( c ) ); //position of destination
//                System.out.println(index);
//                if(FramesPort.get( c )!=BridgesHost.get(index)){                 //if ports are not equal
//                    BridgesHost.remove(index);
//                    BridgesHost.add( index, FramesPort.get( c ));
//                    System.out.println("new port number: "+ BridgesHost.get( index ));//update in FDB
//
//                }
//            }
//        }

        System.out.println();

        //https://www.youtube.com/watch?v=wXSZcuQLklk&ab_channel=shadsluiter
        File fout = new File("BridgeOutput.txt");
        FileWriter flower = new FileWriter( fout );
        PrintWriter pow = new PrintWriter( flower );


        //Now method to Forward, Discard or Broadcast
        for(int counter = 0; counter< FramesPort.size(); counter++) {
            int num, trip = 0;
            String display;
            if (BridgesDestination.contains( FramesDestination.get( counter ) )) {          //if it contains destination address
                num = BridgesDestination.indexOf( FramesDestination.get( counter ) );       //store destination index


               //if frame destination is in the same port as it's incoming port dwe discard.
               if( BridgesHost.get( num )==FramesPort.get( counter )) {
                    display = FramesSource.get( counter ) + "\t" + FramesDestination.get( counter ) + "\t" + FramesPort.get( counter ) + "\t Discarded";
                    System.out.println( display );
                    pow.write( display );
               } else {
                    display = FramesSource.get( counter )+"\t"+FramesDestination.get( counter )+"\t"+FramesPort.get( counter )+"\t Forwarded on port "+BridgesHost.get( num );
                    System.out.println( display );
                    pow.write( display );
               }
            }else {//when the incoming frame destination is not in bridge FDB
                display = FramesSource.get( counter ) + "\t" + FramesDestination.get( counter ) + "\t" + FramesPort.get( counter ) + "\t Broadcast";
                System.out.println( display );
                pow.write( display );
            }
            trip++;

            if(trip>1) {
                updateFDB( FramesPort, FramesSource, BridgesDestination, BridgesHost );
            }
       }
        pow.close();            //https://www.youtube.com/watch?v=wXSZcuQLklk&ab_channel=shadsluiter

    }
    public static void updateFDB(ArrayList<Integer> fp, ArrayList<String> fs, ArrayList<String> bd, ArrayList<Integer> bh){
        for(int c=0; c<fp.size();c++){
            if(bd.contains( fs.get(c) )){               //if it contains source
                int index = bd.indexOf( fs.get( c ) ); //position of destination
                if(fp.get( c )!=bh.get(index)){                 //if ports are not equal
                    bh.remove(index);
                    bh.add( index, fp.get( c ));

                }
            }
        }
    }
}
