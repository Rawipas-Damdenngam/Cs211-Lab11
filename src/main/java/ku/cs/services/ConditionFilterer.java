package ku.cs.services;

public interface ConditionFilterer<T> {
    boolean check(T t);
}
