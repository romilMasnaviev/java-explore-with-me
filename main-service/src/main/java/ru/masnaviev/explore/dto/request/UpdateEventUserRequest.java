package ru.masnaviev.explore.dto.request;

import lombok.Data;

/**
 * Данные для изменения информации о событии. Если поле в запросе не указано (равно null) - значит изменение этих данных не требуется.
 */
@Data
public class UpdateEventUserRequest {
    private String annotation;
    private int category;
    private String description;

}
