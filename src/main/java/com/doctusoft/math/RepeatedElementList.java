package com.doctusoft.math;

import com.doctusoft.annotation.Beta;
import com.doctusoft.java.Failsafe;

import java.util.*;
import java.util.function.*;

import static java.util.Objects.*;

/**
 * A read-only view of {@code n} identical elements in a {@link List} where n must be a positive integer and the
 * {@code element} cannot be {@code null}. All attempts to modify the elements after the instance has been created
 * will result in an {@link UnsupportedOperationException}.
 * <p>Instances of this class may never be {@link #isEmpty() empty}. If 0 as list size should be tolerated upon
 * instance creation, use the {@link #repeat(Object, int)} method.</p>
 *
 * @param <E> the type parameter of the list
 */
@Beta
public final class RepeatedElementList<E> extends AbstractList<E> {
    
    /**
     * @return a read-only {@link List} which has {@code n} identical elements. {@code n} can be {@code null} in which
     * case an {@link Collections#emptyList() empty list} is returned.
     */
    public static <E> List<E> repeat(E element, int nTimes) {
        requireNonNull(element, "element");
        switch (nTimes) {
        case 0:
            return Collections.emptyList();
        case 1:
            return Collections.singletonList(element);
        default:
            return new RepeatedElementList<>(element, nTimes);
        }
    }
    
    private final E element;
    
    private final int size;
    
    /**
     * <b>Important to note</b> that the constructor does not tolerate {@code 0} as {@code size}. If that is needed, use
     * the {@link #repeat(Object, int)} factory method.
     */
    public RepeatedElementList(E element, int size) {
        this.element = requireNonNull(element, "element");
        this.size = size;
        Failsafe.checkArgument(size > 0, "size <= 0");
    }
    
    /**
     * An instance of {@link RepeatedElementList} can never be empty, thus this method will always return {@code false}.
     */
    public boolean isEmpty() { return false; }
    
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index=" + index + ", size=" + size);
        }
        return element;
    }
    
    public int size() { return size; }
    
    @Override public void sort(Comparator<? super E> c) {}
    
    @Override public void forEach(Consumer<? super E> action) {
        for (int i = 0; i < size; ++i) {
            action.accept(element);
        }
    }
    
    @Override public boolean contains(Object obj) {
        return Objects.equals(obj, element);
    }
    
    @Override public boolean removeIf(Predicate<? super E> filter) { throw new UnsupportedOperationException(); }
    
    @Override public void replaceAll(UnaryOperator<E> operator) { throw new UnsupportedOperationException(); }
    
}
