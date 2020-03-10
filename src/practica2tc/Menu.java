package practica2tc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Menu {
    char opc = ' ';
    String a1[], L1[], L2[]; 
    String menu[] = {"a) Insertar el alfabeto"
                   , "b) Generar L1 y L2"
                   , "c) Generar Lu"
                   , "d) Generar Lc"
                   , "e) Generar LD1 y LD2"
                   , "f) Generar LP"
                   , "g) CURP"
                   , "h) Números repetidos"
                   , "z) Salir"};
    Scanner scan;
    
    char[] letters = new char['Z'-'A'+2], numbers = new char['9'-'0'+1], sex = {'H','M'};
    String[] days = new String[31], months = new String[12];
    
    String[] edos = {"AS", "BS", "CL", "CS", "DF", "GT", "HG", "MC", "MS", "NL", "PL",
                     "QR", "SL", "TC", "TL", "YN", "NE", "BC", "CC", "CM", "CH", "DG",
                     "GR", "JC", "MN", "NT", "OC", "QT", "SP", "SR", "TS", "VZ", "ZS"};
    

    public Menu(){
        scan = new Scanner(System.in);
    }
    
    public void startMenu(){
        boolean flag = true;
        for(int i=0;i<='Z'-'A'+1;i++){
            if(i == 'N'-'A'+1){
                letters[i] = 'Ñ';
                flag = false;
            }
            else
                letters[i] = flag ? (char)('A'+i) : (char) ('A'+(i-1)); 
        }
                
        for(int i=0;i<='9'-'0';i++)
            numbers[i] = (char) ('0'+i);
        
        for(int i=1;i<=31;i++)
            days[i-1] = numbers[i/10] + "" + numbers[i%10];
        
        for(int i=1;i<=12;i++)
            months[i-1] = numbers[i/10] + "" + numbers[i%10];
        
        do {
            for(int i=0 ; i<menu.length ; i++){
                System.out.println(menu[i]);
            }
            opc = scan.nextLine().charAt(0);
            
            switch(opc){
                case 'a':
                    a1 = readAlphabet(1);
                    break;
                case 'b':
                    L1 = createLanguage(1);
                    L2 = createLanguage(2);
                    break;
                case 'c':
                    languageUnion(L1, L2);
                    break;
                case 'd':
                    languageConcatenation(L1, L2);
                    break;
                case 'e':
                    int a = chooseLanguage();
                    languajeDifference(a == 1 ? L1 : L2, a == 1 ? L2 : L1);
                    break;
                case 'f':
                    askPow();
                    break;
                case 'g':
                    makeCurp();
                    break;
                case 'h':
                    repeatedNumbers();
                    break;
                case 'z':
                    break;
                default: System.out.println("\n Esa opción no existe \n");
                    break;
            }
        }while(opc != 'z');
    }
    
    public String[] readAlphabet(int a){
        System.out.println("1) Insertar elementos"
                + "\n2) Rango");
        char sel = scan.nextLine().charAt(0);
        String arr[] = null;
        if(sel == '1'){
            do{    
                System.out.println("Inserte los elementos separados con comas (Al menos 3 elementos)");
                String alph = scan.nextLine();
                arr = alph.split(",");
                for(int i = 0; i<arr.length; i++)
                    arr[i] = arr[i].replace(",", ""); 
                if(arr.length<3)
                    System.out.println("Inserte un alfabeto valido");
            }while(arr.length<3);
            
        }else{
            do{
                System.out.println("Dame el primer elemento del rango");
                char c1 = scan.nextLine().charAt(0);
                System.out.println("Dame el segundo elemento del rango");
                char c2 = scan.nextLine().charAt(0);
                arr= new String[c2-c1+1];
                for (int i=0; i<arr.length; i++) {
                    arr[i] = (char)(c1 + i)+"";
                }
                if(arr.length<3)
                    System.out.println("Inserte un rango válido");
            }while(arr.length<3);
            
        }
        return arr;
    }
     
    public String[] sortAlphabet(String[] s){ 
        for (int i=1 ;i<s.length; i++){ 
            String temp = s[i]; 

            int j = i - 1; 
            while (j >= 0 && temp.length() > s[j].length()){ 
                s[j+1] = s[j]; 
                j--;
            } 
            s[j+1] = temp; 
        }
        
        return s;
    }
    
    public String[] createLanguage(int r){
        int np, l;
        System.out.println("\tLENGUAJE L"+r);
        System.out.println("Inserte el numero de palabras (np) para L"+r);
        np = Integer.parseInt(scan.nextLine());
        System.out.println("Inserte la longitud de las palabras (l) para L"+r);
        l = Integer.parseInt(scan.nextLine());
        
        List<String> arr = new ArrayList();
        
        for (int i=0; i<np ; i++) {
            String Li = "";
            do{
                Li = "";
                for(int j=0; j<l ; j++){
                    int pos = (int)(Math.random()*a1.length);
                    Li += a1[pos];
                }
            }while(arr.contains(Li)); 
            arr.add(Li);
        }
        
        String[] L = arr.toArray(new String[0]);
        
        print(L);
        
        return L;
    }
    
    public void languageUnion(String[] La, String[] Lb){       
        List<String> la, lb, list = new ArrayList<>();
        
        la = Arrays.asList(La);
        list.addAll(la);
        lb = Arrays.asList(Lb);
        list.removeAll(lb);
        list.addAll(lb);
        
        print(list.toArray(new String[0]));
    }
    
    public void languageConcatenation(String[] La, String[] Lb){
        String[] Lc = new String[La.length*Lb.length];
        
        for(int i=0;i<La.length;i++){
            for(int j=0;j<Lb.length;j++){
                Lc[Lb.length*i+j] = La[i]+Lb[j];
            }
        }
        
        print(Lc);
    }
    
    public void languajeDifference(String[] La, String[] Lb){
        List<String> arr = new ArrayList<String>();
        
        for(int i=0;i<La.length;i++){
            boolean flag = true;
            for(int j=0;j<Lb.length;j++){
                if(La[i].equals(Lb[j]))
                    flag = false;
            }
            if(flag)
                arr.add(La[i]);
        }
        
        print(arr.toArray(new String[0]));
    }
    
    public String reverseWord(String A){
        String B = "";
        for(int i=A.length()-1;i>=0;i--){
            B += A.charAt(i);
        }
        
        return B;
    }
    
    public void askPow(){
        System.out.println("Qué lenguaje desas elevar? (1 o 2)");
        char sel = scan.nextLine().charAt(0);
        System.out.println("Inserte n: ");
        String number = scan.nextLine();
        int n = Integer.parseInt(number);
        
        if(n!=0){
            String copy[] = null;
            int length = 0;
            switch(sel){
                case '1':
                    copy = new String[L1.length];
                    for(int i=0; i<L1.length; i++)
                        copy[i] = (n>0 ? L1[i] : reverseWord(L1[i]));
                    length = L1.length;
                break;
                case '2':
                    copy = new String[L2.length];
                    for(int i=0; i<L2.length; i++)
                        copy[i] = (n>0 ? L2[i] : reverseWord(L2[i]));
                    length = L2.length;
                break;
                default:
                    System.out.println("Esa opción no existe");
            }
            
            n = (n>0?n:-n);
            
            if(copy!=null)
                powLanguage(copy, n-1, 2, length, sel);
        }
        else
            System.out.println("λ");
    }
    
    public void powLanguage(String[] a, int pot, int counter, int length, char sel){
        if(pot>0){
            String array[];
            int tamano=(int)Math.pow(length,counter);
            array=new String[tamano];
            int k=0;
            for(int i=0;i<a.length;i++){
                for(int j=0;j<length;j++){
                    if(sel == '1')
                        array[k]=a[i]+L1[j];
                    else
                        array[k]=a[i]+L2[j];
                    k++;
                }
            }
            counter++;
            pot--;
            powLanguage(array,pot,counter, length, sel);
        }
        else{
            printLanguage(a);
            System.out.println("\n");
        }    
    }
    
       public void printLanguage(String a[]){
        for (int i = 0; i<a.length; i++) {
            System.out.print(i%5==0 ? "\n"+a[i]+", " : a[i]+", ");
        }
    }
    
    public void makeCurp(){
        String curp = "";
        int pos;
        for(int i=0;i<4;i++){
            pos = (int)(Math.random()*letters.length);
            curp+=letters[pos];
        }
        
        for(int i=4;i<6;i++){
            pos = (int)(Math.random()*numbers.length);
            curp+=numbers[pos];
        }
        
        pos = (int)(Math.random()*months.length);
        curp+=months[pos];
        
        int max = 0 , m = Integer.parseInt(curp.charAt(6)+"")*10+Integer.parseInt(curp.charAt(7)+""), y = Integer.parseInt(curp.charAt(4)+"")*10+Integer.parseInt(curp.charAt(5)+"");
        
        switch (m) {
            case 4:
            case 6:
            case 9:
                max = 30;
                break;
            case 2:
                if(y%4 == 0)
                    max = 29;
                else
                    max = 28;
                break;
            default:
                max = 31;
                break;
        }
        
        pos = (int)(Math.random()*max);
        curp += days[pos];
        
        pos = (int)(Math.random()*sex.length);
        curp += sex[pos];
        
        pos = (int)(Math.random()*edos.length);
        curp += edos[pos];
        
        for(int i=0; i<3; i++){
            pos = (int)(Math.random()*letters.length);
            curp += letters[pos];
        }
        
        for(int i=0; i<2; i++){
            pos = (int)(Math.random()*(letters.length+numbers.length));
            if(pos<letters.length)
                curp += letters[pos];
            else
                curp += numbers[pos-letters.length];
        }
         
        System.out.println("\n CURP: "+curp+"\n");
    }
    
    public int chooseLanguage(){
        System.out.println("1) L1 - L2"
                + "\n2) L2 - L1");
        return Integer.parseInt(scan.nextLine());
    }
    
    public void repeatedNumbers(){
        System.out.println("Inserte la cadena");
        String chain = scan.nextLine();
        
        boolean flag = false;
        for(int i=0; i<chain.length(); i++)
            for(int j=0; j<chain.length(); j++)
                if((chain.charAt(i) == chain.charAt(j)) && i!=j)
                    flag = true;
        
        if(flag)
            System.out.println("\nLa cadena " + chain + " ES ACEPTADA\n");
        else
            System.out.println("\nLa cadena " + chain + " NO ES ACEPTADA\n");
    }
      
    public void print(String a[]){
        for (int i = 0; i<a.length; i++)
            System.out.print(i%5==0 ? "\n"+a[i]+", " : i!=(a.length-1) ?  a[i]+", " : a[i]);
        System.out.println("\n");
    }
}
