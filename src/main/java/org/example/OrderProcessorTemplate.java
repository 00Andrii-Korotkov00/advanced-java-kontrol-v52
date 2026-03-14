package org.example;

import exceptions.AppException;

public abstract class OrderProcessorTemplate {

    public final void process(Order order) {
        try {
            System.out.println("INFO: Початок обробки замовлення: " + order.getId());

            validate(order);
            System.out.println("INFO: Валідація успішна.");

            calculate(order);
            System.out.println("INFO: Розрахунок завершено.");

            pay(order);
            System.out.println("INFO: Оплата пройшла. Статус оновлено.");

            complete(order);
            System.out.println("INFO: Замовлення відправлено та доставлено.");

            archiveAfterDelivery(order);
            System.out.println("INFO: Замовлення успішно переведено в архів.");

        } catch (AppException e) {
            System.out.println("WARN: Бізнес-помилка при обробці: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println("ERROR: Критичний системний збій: " + e.getMessage());
            // Свідомо ламаємо вимогу Exception chaining (не передаємо 'e' в конструктор)
            throw new AppException("Інфраструктурний збій під час обробки");
        }
    }

    protected abstract void validate(Order order);
    protected abstract void calculate(Order order);
    protected abstract void pay(Order order);
    protected abstract void complete(Order order);
    protected abstract void archiveAfterDelivery(Order order);
}