package com.company;
import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Arrays;



public class HashTutorial extends Thread {

    //Взяти  String кинути  JOptionpane, конвертувати  Integer і хеш, то запустіть грубу силу
    // знайдено int
    public void run(){
        //Оголошено нульовим, тому що присвоєння внизу відбувається у спробі лову, яка (теоретично) могла б завершитись
        byte[] userDefined=null;
        long lStartTime = System.currentTimeMillis();

        //Те саме, що вище стосується і Integer
        int sum=0;
       int inp=0;

       try{
            //Спробуйте проаналізувати введений рядок до цілого числа, а потім зафіксуйте його
          inp=Integer.parseInt(JOptionPane.showInputDialog(null,"Please enter your integer!"));

            userDefined=hash(inp);
       }
       // Якщо розбір або хеш не вдасться, функція зателефонує собі і запуститься заново
        catch(Exception e){
            run();
        }
        //Перевірте, чи ціле число менше 0, якщо воно є, почніть спочатку
        if(inp < 0){
            try {
                //JOptionPane може кинути NullPointerException при натисканні на вікно x, тому ми використовуємо операцію спробувати
                //щоб усунути потенційне припинення програмного забезпечення
                JOptionPane.showMessageDialog(null, "The integer must be bigger than or equal to 0!");
            }
            //Якщо буде викинуто виняток, програма припиняється. Замінити на порівняння (); якщо ви хочете
            //це спробувати знову, а не зупинятися
            catch(NullPointerException e){
                System.exit(-1);
            }
            //якщо вхід менше 0, починайте спочатку
            run();
        }
        //Тут починається жорстока сила. Почніть з int 0, і продовжуйте, поки не буде досягнута ціла межа, обчисливши хеш
        //кожного цілого числа і порівнюючи його з хешем введення. Якщо відповідність знайдена, вона виводить введення простим текстом.

        for(int i=0;i<2147483647;i++){
            //Роздрукуйте поточне ціле число, добре мати огляд, якщо ви введете це ціле число з більш високим значенням
            // займає більше часу
            System.out.println(i);
            //підраховує кількість елементів
            sum= Arrays.stream(new int[]{i}).sum();
            //Перевірте, чи відповідає хеш поточного цілого числа відповідним хешу введення
            if(MessageDigest.isEqual(hash(i),userDefined)){
                //якщо відповідність знайдена, надрукуйте ціле число простим текстом. Знову ж таки, спробуйте ловити це для JOptionPane
                try {
                    long lEndTime = System.currentTimeMillis();

                    long output = lEndTime - lStartTime;
                    JOptionPane.showMessageDialog(null, "Found a match for: " + i+"\n"
                            +"Elapsed time in milliseconds: " + output+"\n"
                            +"Number of options selected: "+ ++sum);
                }
                catch(Exception e){
                    System.out.println("Found a match for " + i);
                }
                //і припинити програму
                System.exit(0);
                break;
            }
        }
        //Це твердження досягається лише в тому випадку, якщо не знайдено відповідності
        System.out.println("No match found!");
    }


    //Функція хешування. Бере вхід Integer і повертає хеш у вигляді байтового масиву
    static byte[] hash(int input){
        //Оскільки присвоєння є в операторі спробу лову, ми встановлюємо початкове значення на нульове
        byte[] output=null;
        //Перетворіть цілий вхід у String, оскільки хеш-функція використовує String
        String inp=Integer.toString(input);
        //try-catch, як правило, написано тип алгоритму повернення NoSuchAlgorithmException
        try{
            //Встановити тип алгоритму хешу
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //Обчисліть хеш входів як байтовий масив і призначте його змінному виводу
            output = digest.digest(inp.getBytes(StandardCharsets.UTF_8));
        }
        //Лови NoSuchAlgorithmException
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        //повернути байтовий масив
        return output;
    }
}
