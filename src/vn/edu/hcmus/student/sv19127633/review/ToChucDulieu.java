package vn.edu.hcmus.student.sv19127633.review;

import java.io.*;
import java.util.*;

public class ToChucDulieu {
    Random rd=new Random();

    private HashMap<String, String> map=new HashMap<>();
    private HashMap<String, String> revMap=new HashMap<>();
    private ArrayList<String> history=new ArrayList<>();

    public ToChucDulieu() throws IOException {
        boolean check=generate("latest_slang.txt");
        System.out.println(check);
        if (check == false)
            generate("slang.txt");
    }

    public HashMap<String,String> getMap()
    {
        return map;
    }

    public HashMap<String,String> getRevMap()
    {
        return revMap;
    }

    public ArrayList<String> getHistory(){return history;}

    public boolean generate(String filename) throws IOException {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            in.readLine();

            String keyOld = "";
            String valueOld = "";

            String line = in.readLine();
            String[] buffer = null;

            while (line != null) {
                buffer = line.split("`");
                if (buffer.length == 2) {
                    map.put(buffer[0], buffer[1]);

                    if (valueOld != "")
                        revMap.put(valueOld, keyOld);
                    valueOld = buffer[1];
                } else {
                    String temp = map.get(keyOld);
                    map.replace(keyOld, temp, temp + "| " + buffer[0]);

                    valueOld += buffer[0];
                }
                keyOld = buffer[0];
                line = in.readLine();

            }
            revMap.put(valueOld, keyOld);
            in.close();
            return true;

        } catch (IOException e) {
            return false;
        }
    }

    public void save(String filename) throws IOException {
        BufferedWriter out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));

        Set<String> keys = map.keySet();

        keys.forEach((key) ->
        {
            try {
                out.write(key+"`"+map.get(key)+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        out.close();
    }

    public void show(boolean rev)
    {
        if (!rev) {
            Set<String> keys = map.keySet();

            keys.forEach((key) ->
            {
                System.out.println("Key: " + key + " Value: " + map.get(key));
            });
        }
        else
        {
            Set<String> keys = revMap.keySet();

            keys.forEach((key) ->
            {
                System.out.println("Key: " + key + " Value: " + revMap.get(key));
            });
        }
    }

    public String findDef(String slang)
    {
        history.add(slang);
        return map.get(slang);
    }

    public ArrayList<String> findSlang(String def)
    {
        //check empty
        ArrayList<String> list=new ArrayList<>();
        String [] splitDef=def.split(" ");
        Set<String> keys = map.keySet();

        keys.forEach((key) ->
        {
            String temp=map.get(key);
            String[] splitKey=temp.split(" ");
            int count=0;

            for (int i=0;i<splitDef.length;i++)
            {
                for (int j=0;j<splitKey.length;j++)
                {
                    if (splitDef[i].equals(splitKey[j])) {

                        count++;
                        break;
                    }
                }
            }
            if (count==splitDef.length)
                list.add(key);

        });
        return list;
    }

    public String addSlang(String slang,String def) throws IOException {
        if (map.containsKey(slang)) //Exist
            return "Duplicate";
        else {
            map.put(slang, def);
            revMap.put(def,slang);
            history.add(slang);
            save("latest_slang.txt");
        }
        return "Successful";
    }

    public Boolean editSlang(String oldSlang,String newSlang,String newDef) throws IOException {
        //check empty
        if(map.containsKey(oldSlang))
        {
            map.remove(oldSlang);
            map.put(newSlang,newDef);
            revMap.remove(map.get(oldSlang));
            revMap.put(newDef,newSlang);
            history.add(newSlang);
            save("latest_slang.txt");
            return true;
        }
        else //Not exist
            return false;
    }

    public  Boolean deleteSlang(String slang) throws IOException {
        //check empty
        if(map.containsKey(slang)) //exist
        {
            //confirm
            map.remove(slang);
            revMap.remove(map.get(slang));
            history.add(slang);
            save("latest_slang.txt");
            return true;
        }
        else //not exist
            return false;
    }

    public void reset() throws IOException {
        map.clear();
        generate("slang.txt");
        save("latest_slang.txt");
    }

    public String addDefWithoutDuplicate(ArrayList<String> strings)
    {
        while (true)
        {
            String def=randDef();
            for (int i=0;i<strings.size();i++)
            {
                if(strings.get(i)==def)
                    break;
                if(i==strings.size()-1)
                    return def;
            }
        }
    }

    public String addSlangWithoutDuplicate(ArrayList<String> strings)
    {
        while (true)
        {
            String def=randSlang();
            for (int i=0;i<strings.size();i++)
            {
                if(strings.get(i)==def)
                    break;
                if(i==strings.size()-1)
                    return def;
            }
        }
    }

    public String randSlang()
    {
        return (String)map.keySet().stream().toArray()[rd.nextInt(map.size()-1)];
    }

    public String randDef()
    {
        return map.get(randSlang());
    }

    public ArrayList<String> funnyQuestion1()
    {
        String slang=randSlang();
        ArrayList<String>strings=new ArrayList<>();

        strings.add(map.get(slang));
        strings.add(addDefWithoutDuplicate(strings));
        strings.add(addDefWithoutDuplicate(strings));
        strings.add(addDefWithoutDuplicate(strings));

        for (int i=0;i<4;i++)
            Collections.swap(strings,i,rd.nextInt(4));

        strings.add(slang);
        return strings;
    }

    public ArrayList<String> funnyQuestion2()
    {
        String slang=randSlang();
        ArrayList<String>strings=new ArrayList<>();

        strings.add(slang);
        strings.add(addSlangWithoutDuplicate(strings));
        strings.add(addSlangWithoutDuplicate(strings));
        strings.add(addSlangWithoutDuplicate(strings));

        for (int i=0;i<4;i++)
            Collections.swap(strings,i,rd.nextInt(4));

        strings.add(map.get(slang));
        return strings;
    }

}
