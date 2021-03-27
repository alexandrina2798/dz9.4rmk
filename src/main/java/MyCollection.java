package main.java;


import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class MyCollection<E> implements Collection<E> {

    private int size;

    private Object[] elementData = new Object[10];

    static <E> Collection<E> getCollection(final E... elements) {
        Collection<E> c = new MyCollection<>();
        for (E e : elements) {
            c.add(e);
        }
        return c;
    }

    @Override
    public boolean add(final E e) {
        if (size == elementData.length) {
            elementData = Arrays.copyOf(elementData, (int) ((size + 1) * 1.5f));
        }
        elementData[size++] = e;
        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return !iterator().hasNext();
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator<>();
    }

    @Override
    public boolean contains(final Object o) {
        boolean result = false;
        Iterator<Integer> it = (Iterator<Integer>) this.iterator();
        while (it.hasNext()) {
            Object temp = it.next();
            if (temp != null) {
                if (temp.equals(o)) {
                    result = true;
                    break;
                } else {
                    if (o == null) {
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Object[] toArray() {
        Object[] answer = new Object[size];
        int i = 0;
        Iterator<Integer> it = (Iterator<Integer>) this.iterator();
        while (it.hasNext()) {
            answer[i] = it.next();
            i += 1;
        }
        return answer;
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        int l = size;
        if (l < a.length) {
            l = a.length;
        }
        T[] answer = (T[]) new Object[l];
        int i = 0;
        Iterator<Integer> it = (Iterator<Integer>) this.iterator();
        while (it.hasNext()) {
            answer[i] = (T) it.next();
            i += 1;
        }

        for (; i < l; i++) {
            answer[i] = a[i];
        }

        return answer;
    }

    @Override
    public boolean remove(final Object o) {
        boolean result = false;
        Iterator<Integer> it = (Iterator<Integer>) this.iterator();
        while (it.hasNext()) {
            Object temp = it.next();
            if (temp != null) {
                if (temp.equals(o)) {

                    it.remove();
                    result = true;
                    break;
                }
            } else {
                if (o == null) {
                    it.remove();
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        Iterator<Integer> it = (Iterator<Integer>) c.iterator();
        while (it.hasNext()) {
            Object temp = it.next();
            if (!this.contains(temp)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        Iterator<Integer> it = (Iterator<Integer>) c.iterator();
        while (it.hasNext()) {
            if (size == elementData.length) {
                elementData = Arrays.copyOf(elementData, (int) (size * 1.5f));
            }
            elementData[size++] = it.next();
        }
        return true;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        boolean result = false;
        Iterator<Integer> it = (Iterator<Integer>) this.iterator();

        while (it.hasNext()) {
            Iterator<Integer> udoli = (Iterator<Integer>) c.iterator();
            Object temp = it.next();
            while (udoli.hasNext()) {
                Object udoliTemp = udoli.next();
                if (temp != null) {
                    if (temp.equals(udoliTemp)) {
                        it.remove();
                        result = true;
                        break;
                    }
                } else {
                    if (udoliTemp == null) {
                        it.remove();
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        boolean answer = false;
        Iterator<Integer> it = (Iterator<Integer>) this.iterator();

        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                answer = true;
                it.remove();
            }
        }

        return answer;
    }

    @Override
    public void clear() {
        this.removeAll(this);
        this.removeAll(this);
    }

    private class MyIterator<T> implements Iterator<T> {

        int cursor = 0;
        boolean n = false;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public T next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            n = true;
            return (T) elementData[cursor++];
        }

        @Override
        public void remove() throws IllegalStateException {
            if (!n) {
                throw new IllegalStateException("remove");
            }
            try {
                while (cursor < size) {
                    elementData[cursor - 1] = elementData[cursor];
                    cursor++;
                }
                size--;
                cursor = 1;
                n = false;
            } catch (UnsupportedOperationException e) {
                throw new UnsupportedOperationException("remove");
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ArrayIndexOutOfBoundsException("remove");
            } catch (IllegalStateException e) {
                throw new IllegalStateException("remove");
            }
        }

    }
}