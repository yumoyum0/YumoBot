package top.yumoyumo.yumobot.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/11/30 21:43
 **/
public class Builder<T> {
    /**
     * 存储调用方 指定构造类的 构造器
     */
    private final Supplier<T> constructor;
    /**
     * 存储 指定类 所有需要初始化的类属性
     */
    private final List<Consumer<T>> dInjects = new ArrayList<>();

    public Builder(Supplier<T> constructor) {
        this.constructor = constructor;
    }

    public static <T> Builder<T> builder(Supplier<T> constructor) {
        return new Builder<>(constructor);
    }

    public <P1> Builder<T> with(Builder.DInjectConsumer<T, P1> consumer, P1 p1) {
        Consumer<T> c = instance -> consumer.accept(instance, p1);
        dInjects.add(c);
        return this;
    }

    public T build() {
        // 调用supplier 生成类实例
        T instance = constructor.get();
        // 调用传入的setter方法，完成属性初始化
        dInjects.forEach(dInjects -> dInjects.accept(instance));
        // 返回 建造完成的类实例
        return instance;
    }

    @FunctionalInterface
    public interface DInjectConsumer<T, P1> {
        void accept(T t, P1 p1);
    }


    public static void main(String[] args) {
//        Student student = new Student();
//        student.setAge(18);;
//        student.setName("yumo");
        Student student = Builder.builder(Student::new).with(Student::setName, "yumo").with(Student::setAge, 18).build();
        System.out.println(student);
    }

    static class Student {
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }


}
