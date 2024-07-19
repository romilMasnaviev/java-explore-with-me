package ru.practicum.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.practicum.dto.comment.CommentFullDto;
import ru.practicum.dto.comment.NewCommentDto;
import ru.practicum.model.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
@Component("commentConverter")
public interface CommentConverter {

    Comment newCommentDtoConvertToComment(NewCommentDto newComment);

    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "authorId", source = "author.id")
    CommentFullDto commentConvertToFullCommentDto(Comment comment);

    List<CommentFullDto> commentConvertToFullCommentDto(List<Comment> comments);
}
