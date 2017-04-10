package nl.tue.dbsu10.group6.gestures.util;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A container object which may or may not contain a non-null value. If a value is present, {@code
 * isPresent()} will return {@code true} and {@code get()} will return the value.
 * <p>
 * Most documentation is taken directly from the Java 8 version of Optional. This class is just here
 * because Android is still at Java 7.
 *
 * @param <T>
 *         The class of the value.
 * @author Ruben Schellekens
 */
public class Optional<T> {

    /**
     * Common instance for {@code empty()}.
     */
    private static final Optional<?> EMPTY = new Optional<>(null);

    /**
     * The value contained in the optional.
     * <p>
     * {@code null} when no value is contained in the optional.
     */
    private T value;

    /**
     * The (nullable) initial value.
     *
     * @param value
     *         The initial contained value. May be {@code null}.
     */
    private Optional(T value) {
        this.value = value;
    }

    /**
     * Returns an {@code Optional} with the specified present non-null value.
     *
     * @param <T>
     *         the class of the value
     * @param value
     *         the value to be present, which must be non-null
     * @return an {@code Optional} with the value present
     * @throws NullPointerException
     *         if value is null
     */
    public static <T> Optional<T> of(T value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }

        return new Optional<>(value);
    }

    /**
     * Returns an {@code Optional} describing the specified value, if non-null, otherwise returns an
     * empty {@code Optional}.
     *
     * @param <T>
     *         the class of the value
     * @param valueOrNull
     *         the possibly-null value to describe
     * @return an {@code Optional} with a present value if the specified value is non-null,
     * otherwise an empty {@code Optional}
     */
    public static <T> Optional<T> ofNullable(T valueOrNull) {
        return new Optional<>(valueOrNull);
    }

    /**
     * Returns an empty {@code Optional} instance.  No value is present for this Optional.
     *
     * @param <T>
     *         Type of the non-existent value
     * @return an empty {@code Optional}
     */
    public static <T> Optional<T> empty() {
        return (Optional<T>)EMPTY;
    }

    /**
     * If a value is present in this {@code Optional}, returns the value, otherwise throws {@code
     * NoSuchElementException}.
     *
     * @return the non-null value held by this {@code Optional}
     * @throws NoSuchElementException
     *         if there is no value present
     * @see Optional#isPresent()
     */
    public T get() throws NoSuchElementException {
        if (value == null) {
            throw new NoSuchElementException("no value present");
        }

        return value;
    }

    /**
     * Return {@code true} if there is a value present, otherwise {@code false}.
     *
     * @return {@code true} if there is a value present, otherwise {@code false}
     */
    public boolean isPresent() {
        return value != null;
    }

    /**
     * Return the value if present, otherwise return {@code other}.
     *
     * @param otherwise
     *         the value to be returned if there is no value present, may be null
     * @return the value, if present, otherwise {@code otherwise}
     */
    public T orElse(T otherwise) {
        return isPresent() ? value : otherwise;
    }

    /**
     * Return the contained value, if present, otherwise throw the given exception.
     *
     * @param exception
     *         The exception to throw when there is no present value.
     * @return the present value
     * @throws NullPointerException
     *         if no value is present and {@code exceptionSupplier} is {@code null}.
     */
    public T orElseThrow(RuntimeException exception) throws RuntimeException {
        if (!isPresent()) {
            throw exception;
        }

        return value;
    }

    /**
     * Format: {@code "Optional.empty"} when the Optional is empty, {@code "Optional[%s]"} when
     * there is a value present.
     */
    @Override
    public String toString() {
        if (isPresent()) {
            return "Optional[" + value + "]";
        }
        else {
            return "Optional.empty";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Optional)) {
            return false;
        }

        Optional<?> optional = (Optional<?>)o;
        return value != null ? value.equals(optional.value) : optional.value == null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
