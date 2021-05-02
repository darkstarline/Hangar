package gundam.utils;


import com.google.common.base.Function;

public class ThreadUtils {
    public static <F,T> void newThread(T arg, Function<? super T,F> function){
        new InnerThread<F ,T>(arg, function).start();
    }
    static class InnerThread<F,T> extends Thread{
        final T argValue;
        final Function<? super T, ? extends F> function;
        public InnerThread(T arg,Function <? super T, ? extends F> function){
            this.argValue = arg;
            this.function = function;
        }
        @Override
        public void run(){ this.function.apply(this.argValue);}
    }
}
