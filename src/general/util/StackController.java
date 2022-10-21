package general.util;

import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class StackController {

    public static List<Scene> stack = new ArrayList<>();

    public static void addToStack(Scene scene){
        if(stack!=null){
            stack.add(scene);
        }
    }

    public static void removeCurrentFromStack(){
        if(stack!=null && stack.size() > 0){
            stack.remove(stack.size()-1 );
        }
    }

    public static void removeStackAt(int pos){
        if(stack!=null && stack.size()>0){
            stack.remove(pos);
        }
    }


    public static Scene getLastScene(){
        if(stack!=null && stack.size() > 0){
            return stack.get(stack.size()-1);
        }
        return null;
    }

}
