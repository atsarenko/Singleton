
public class Singleton {

    //generate
    private static Singleton ourInstance = new Singleton();

    private Singleton() {
    }

    public static Singleton getInstance() {
        return ourInstance;
    }

//without stream
//    private static Singleton instance;
//    public String value;
//
//    private Singleton(String value) {
//        // Этот код эмулирует медленную инициализацию.
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
//        this.value = value;
//    }
//
//    public static Singleton getInstance(String value) {
//        if (ourInstance == null) {
//            ourInstance = new Singleton(value);
//        }
//        return ourInstance;
//    }


    //with stream
    //Поле обязательно должно быть объявлено volatile, чтобы двойная проверка
    //блокировки сработала как надо.
    private static volatile Singleton instance;
    public String value;

    private Singleton(String value) {
        this.value = value;
    }

    public static Singleton getInstance(String value) {
        // Тезника, которую мы здесь применяем называется
        // «блокировка с двойной проверкой» (Double-Checked Locking).
        // Она применяется, чтобы предотвратить создание нескольких объектов-одиночек,
        // если метод будет вызван из нескольких потоков одновременно.
        //
        // Хотя переменная `result` вполне оправданно кажется здесь лишней, она
        // помогает избежать подводных камней реализации DCL в Java.
        //
        // Больше об этой проблеме можно почитать здесь:
        // https://refactoring.guru/ru/java-dcl-issue
        Singleton result = instance;
        if (result != null) {
            return result;
        }
        synchronized (Singleton.class) {
            if (instance == null) {
                instance = new Singleton(value);
            }
            return instance;
        }
    }
}
