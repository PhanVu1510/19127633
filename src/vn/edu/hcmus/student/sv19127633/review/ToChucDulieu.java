package vn.edu.hcmus.student.sv19127633.review;

import java.io.*;
import java.util.*;

public class ToChucDulieu {
    Random rd=new Random();

    private HashMap<String, String> map=new HashMap<>();
    private HashMap<String, String> revMap=new HashMap<>();
    private ArrayList<String> history=new ArrayList<>();


    public ToChucDulieu(String filename, boolean gen) throws IOException {
        if (gen) {
            generate(filename);
        }
/*        else {
            //generate(n);
            save(filename);
        }*/
        //show(true);

    }

    public HashMap<String,String> getMap()
    {
        return map;
    }

    public HashMap<String,String> getRevMap()
    {
        return revMap;
    }

    public void generate(String filename) throws IOException {
        try
        {
            BufferedReader in =new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            in.readLine();

            String keyOld ="";
            String valueOld="";

            String line=in.readLine();
            String [] buffer=null;

            while (line != null)
            {
                buffer=line.split("`");
                if (buffer.length==2)
                {
                    map.put(buffer[0],buffer[1]);

                    if(valueOld!="")
                        revMap.put(valueOld,keyOld);
                    valueOld=buffer[1];
                }
                else
                {
                    String temp=map.get(keyOld);
                    map.replace(keyOld,temp,temp+"| "+buffer[0]);

                    valueOld+=buffer[0];
                }
                keyOld=buffer[0];
                line=in.readLine();

            }
            revMap.put(valueOld,keyOld);

        } catch (IOException e)
        {
            System.out.println("Khong ton tai file du lieu");
        }

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
    public void save(String filename) throws IOException {
        BufferedWriter out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
        Set <String> keys=map.keySet();
        keys.forEach((key) ->
        {
            try {
                out.write(key+"\n");
            } catch (IOException e) {
                System.out.println(e);
            }
        });
        out.close();
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
            String tempDef=map.get(key);
            boolean flag=false;
            for (int i=0;i<splitDef.length;i++)
            {
                if(tempDef.indexOf(splitDef[i])!=-1)
                    flag = true;
                if (flag==false)
                    break;
                flag=false;
                if(i==splitDef.length-1) {
                    list.add(key);
                    history.add(key);
                }
            }
        });
        return list;
    }

    public Boolean addSlang(String slang,String def)
    {
        //check empty
        if (map.containsKey(slang)) //Exist
        {
          //confirm
            System.out.println("Duplicate");
            return false;
        }
        else {
            history.add(slang);
            map.put(slang, def);
            revMap.put(def,slang);
        }

        return true;
    }

    public Boolean editSlang(String oldSlang,String newSlang,String newDef)
    {
        //check empty
        if(map.containsKey(oldSlang))
        {
            map.remove(oldSlang);
            map.put(newSlang,newDef);
            revMap.put(newDef,newSlang);
            history.add(newSlang);
            return true;
        }
        else //Not exist
        {
            return false;
        }
    }

    public  Boolean deleteSlang(String slang)
    {
        //check empty
        if(map.containsKey(slang)) //exist
        {
            //confirm
            map.remove(slang);
            revMap.remove(map.get(slang));
            history.add(slang);
            return true;
        }
        else //not exist
        {
            return false;
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
