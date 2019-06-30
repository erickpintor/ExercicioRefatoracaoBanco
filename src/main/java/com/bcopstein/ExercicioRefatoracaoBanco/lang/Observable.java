package com.bcopstein.ExercicioRefatoracaoBanco.lang;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public final class Observable<E> {

    private final List<WeakReference<Consumer<E>>> observers = new LinkedList<>();
    private E subject;

    public Observable(E subject) {
        this.subject = subject;
    }

    public E get() {
        return subject;
    }

    public void set(E subject) {
        this.subject = subject;
        notifyObservers();
    }

    public void update(Consumer<E> updater) {
        updater.accept(subject);
        notifyObservers();
    }

    public Object addObserver(Consumer<E> observer) {
        WeakReference<Consumer<E>> ref = new WeakReference<>(observer);
        observers.add(ref);
        return ref;
    }

    public void removeObserver(Object identifier) {
        if (identifier instanceof WeakReference) {
            observers.remove(identifier);
        }
    }

    public void notifyObservers() {
        Iterator<WeakReference<Consumer<E>>> iter = observers.iterator();

        while (iter.hasNext()) {
            WeakReference<Consumer<E>> ref = iter.next();
            Consumer<E> observer = ref.get();

            if (observer != null) {
                observer.accept(subject);
            } else {
                iter.remove();
            }
        }
    }
}
