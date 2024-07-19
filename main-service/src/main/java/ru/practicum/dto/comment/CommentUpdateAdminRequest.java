package ru.practicum.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CommentUpdateAdminRequest {
    @NotNull(message = "Текст комментария не должен быть пустым")
    @NotBlank(message = "Текст комментария не должен содержать только пробельные символы")
    @Size(min = 20, max = 500, message = "Текст комментария должен быть от {min} до {max} символов")
    private String text;
    private boolean isEdited;
}