package com.mygdx.wave;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

public class WaveCollapseFunction {
    public static int[][] createMap(int size){
        int[][] map = new int[size][size];
        ArrayList<ArrayList<TreeSet<Integer>>> table = new ArrayList<ArrayList<TreeSet<Integer>>>();
        for (int i = 0; i < size; i++){
            table.add(new ArrayList<TreeSet<Integer>>());
            for(int j = 0; j < size; j++){
                table.get(i).add(new TreeSet<Integer>());
                map[i][j] = -1;
                for(int t = 0; t < 16; t++){
                    table.get(i).get(j).add(t);
                }
            }
        }

        ArrayList<Prototype> prototypes = new ArrayList<>(set_all_prot());

        int r = MathUtils.random(size-1);
        table.get(r).get(r).clear();
        table.get(r).get(r).add(MathUtils.random(15));

        int min = 16;
        while (min != 17) {
            boolean flag = true;
            while (flag) {
                flag = false;
                for (int i = 0; i < table.size(); i++) {
                    for (int j = 0; j < table.get(i).size(); j++) {
                        if (table.get(i).get(j).size() == 1) {
                            int numProt = table.get(i).get(j).first();
                            for (int x = 0; x < 16; x++) {
                                if (prototypes.get(x).negX != prototypes.get(numProt).posX && !isOut(j + 1, size)) {
                                    table.get(i).get(j + 1).remove(x);
                                }
                                if (prototypes.get(x).posX != prototypes.get(numProt).negX && !isOut(j - 1, size)) {
                                    table.get(i).get(j - 1).remove(x);
                                }
                                if (prototypes.get(x).negY != prototypes.get(numProt).posY && !isOut(i - 1, size)) {
                                    table.get(i - 1).get(j).remove(x);
                                }
                                if (prototypes.get(x).posY != prototypes.get(numProt).negY && !isOut(i + 1, size)) {
                                    table.get(i + 1).get(j).remove(x);
                                }
                            }
                            map[i][j] = table.get(i).get(j).first();
                            table.get(i).get(j).clear();
                            flag = true;
                        }
                    }
                }
            }

            min = 17;
            int[] min_el = new int[2];
            for (int i = 0; i < table.size(); i++) {
                for (int j = 0; j < table.get(i).size(); j++) {
                    if (!(min < table.get(i).get(j).size() || table.get(i).get(j).size() <= 1)) {
                        min_el[0] = i;
                        min_el[1] = j;
                        min = table.get(i).get(j).size();
                    }
                    System.out.print(String.valueOf(table.get(i).get(j).size()) + " ");
                }
                System.out.println();
            }
            System.out.println(min);
            if (min != 17){
                r = MathUtils.random(table.get(min_el[0]).get(min_el[1]).toArray().length-1);
                int first = (int) table.get(min_el[0]).get(min_el[1]).toArray()[r];
                table.get(min_el[0]).get(min_el[1]).clear();
                table.get(min_el[0]).get(min_el[1]).add(first);
            }

            for (int i = 0; i < map.length; i++){
                System.out.println(Arrays.toString(map[i]));
            }
        }



        return map;
    }
    private static ArrayList<Prototype> set_all_prot(){
        ArrayList<Prototype> prototypes = new ArrayList<>();
        prototypes.add(new Prototype(1,0,0,1));  //0  +
        prototypes.add(new Prototype(0,1,0,1));  //1  +
        prototypes.add(new Prototype(1,1,0,1));  //2  +
        prototypes.add(new Prototype(0,1,1,1));  //3  +
        prototypes.add(new Prototype(0,0,0,1));  //4  +
        prototypes.add(new Prototype(0,0,0,0));  //5  +
        prototypes.add(new Prototype(1,0,1,0));  //6  +
        prototypes.add(new Prototype(0,1,1,0));  //7  ++
        prototypes.add(new Prototype(1,0,1,1));  //8  +
        prototypes.add(new Prototype(1,1,1,0));  //9  ++
        prototypes.add(new Prototype(0,0,1,0));  //10 +
        prototypes.add(new Prototype(1,1,0,0));  //11 +
        prototypes.add(new Prototype(1,1,1,1));  //12 +
        prototypes.add(new Prototype(1,1,0,0));  //13 ++
        prototypes.add(new Prototype(0,0,1,1));  //14 +
        prototypes.add(new Prototype(1,0,0,0));  //15 +
        return prototypes;
    }

    private static boolean isOut(int a, int size){
        return a < 0 || a > size-1? true : false;
    }
}
