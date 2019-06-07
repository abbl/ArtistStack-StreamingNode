package ovh.abbl.streamnode.tools;

import java.io.*;

public abstract class BinaryOperations {

    public static byte[] getPartOfTheArray(int startIndex, int endIndex, byte[] bytes){
        byte[] slicedArray = new byte[endIndex - startIndex];
        //System.out.println("B:" + bytes.length + "startIndex: " + startIndex + "endIndex: " + endIndex + "SArray: " + slicedArray.length);
        System.arraycopy(bytes, startIndex, slicedArray, 0, slicedArray.length);

        return slicedArray;
    }

    public static byte[] toByteArray(Object object){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutput objectOutput;

        try {
            objectOutput = new ObjectOutputStream(byteArrayOutputStream);
            objectOutput.writeObject(object);
            objectOutput.flush();

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void writeToFile(byte[] bytes){
        try(FileOutputStream fileOutputStream = new FileOutputStream("test.wav")){
            fileOutputStream.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
