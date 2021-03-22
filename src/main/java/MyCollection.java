package main.java;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyCollection<E> implements Collection<E> {

    private int size;

    private Object[] elementData;

    MyCollection(Object[] elementData){
        this.elementData = elementData;
        size = elementData.length;
    }

    @Override
    public boolean add(E e) {
        if (size == elementData.length) {
            elementData = Arrays.copyOf(elementData, (int) ((size+1) * 1.5f));
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
    public boolean contains(Object o) {
        boolean result = false;
        Iterator<Integer> it = (Iterator<Integer>) this.iterator();
        while (it.hasNext()){
            if (it.next().equals(o)){
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public Object[] toArray() {
        Object[] answer = new Object[this.size];
        Iterator<Integer> it = (Iterator<Integer>) this.iterator();
        for (int i =0; i<this.size; i++){
            answer[i] = it.next();
        }
        return answer;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        T[] answer = null;
        for (int i =0; i<a.length; i++){
            answer[i] = a[i];
        }
        return answer;
    }

    @Override
    public boolean remove(Object o) {
        boolean result = false;
        Iterator<Integer> it = (Iterator<Integer>) this.iterator();
        while (it.hasNext()){
            if (it.next().equals(o)){
                it.remove();
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Iterator<Integer> it = (Iterator<Integer>) c.iterator();
        while (it.hasNext()) {
            if (!this.contains(it.next())){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Iterator<Integer> it = (Iterator<Integer>) c.iterator();
        while (it.hasNext()){
            if (size == elementData.length) {
                elementData = Arrays.copyOf(elementData, (int) (size * 1.5f));
            }
            elementData[size++] = it.next();
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = false;
        Iterator<Integer> it = (Iterator<Integer>) this.iterator();
        while (it.hasNext()) {
            Integer temp = it.next();
            //System.out.println("temp " + temp);
            Iterator<Integer> udoli = (Iterator<Integer>) c.iterator();
            while (udoli.hasNext()) {
                Integer tempUdoli = udoli.next();
                if (tempUdoli.equals(temp)){
                    //System.out.println("tempUdoli " + tempUdoli);
                    it.remove();
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean answer = false;
        Iterator<Integer> it = (Iterator<Integer>) this.iterator();
        Iterator<Integer> udoli = (Iterator<Integer>) c.iterator();
        System.out.println("start");
        while (it.hasNext()){
            Integer temp = it.next();
            if (udoli.hasNext()){
                Integer tempUdoli = udoli.next();
                System.out.println("temp: " + temp + "|tempUdoli: " + tempUdoli);
                if (!c.contains(temp)){
                    this.removeAll(Arrays.asList(temp));
                    answer = true;
                }
            }
        }
        return answer;
    }

    @Override
    public void clear() {
        if (this.iterator().hasNext()){
            while (this.iterator().hasNext()){
                Iterator<Integer> it = (Iterator<Integer>) this.iterator();
                this.removeAll(Arrays.asList(it.next()));
            }
        }
    }

    private class MyIterator<T> implements Iterator<T> {

        int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if(cursor >= size){
                throw new NoSuchElementException();
            }
            return (T) elementData[cursor++];
        }

        @Override
        public void remove() {
            try {
                while (cursor<size){
                    elementData[cursor-1] = elementData[cursor];
                    cursor++;
                }
                size--;
                cursor=1;
            }
            catch (UnsupportedOperationException e){
                throw new UnsupportedOperationException("remove");
            }
            catch (IllegalStateException e){
                System.out.println(e);
            }
        }
    }
}