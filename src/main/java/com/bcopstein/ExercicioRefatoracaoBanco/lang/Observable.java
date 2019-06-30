package com.bcopstein.ExercicioRefatoracaoBanco.lang;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public final class Observable<E> {

    private final List<WeakReference<Consumer<E>>> observers = new LinkedList<>();
    private final List<E> elements;

    public Observable(List<E> elements) {
        this.elements = elements;
    }

    public List<E> unwrap() {
        return elements;
    }

    public void add(E element) {
        elements.add(element);
        notifyAdded(element);
    }

    public void onAdded(Consumer<E> fn) {
        observers.add(new WeakReference<>(fn));
    }

    public Observable<E> filter(Function<E, Boolean> predicate) {
        List<E> newList = new LinkedList<>();

        for (E element : elements) {
            if (predicate.apply(element)) {
                newList.add(element);
            }
        }

        Observable<E> newObservable = new Observable<>(newList);

        onAdded(e -> {
            if (predicate.apply(e)) {
                newObservable.add(e);
            }
        });

        return newObservable;
    }

    private void notifyAdded(E element) {
        Iterator<WeakReference<Consumer<E>>> refs = observers.iterator();

        while (refs.hasNext()) {
            WeakReference<Consumer<E>> ref = refs.next();
            Consumer<E> observer = ref.get();

            if (observer != null) {
                observer.accept(element);
            } else {
                refs.remove();
            }
        }
    }
}
