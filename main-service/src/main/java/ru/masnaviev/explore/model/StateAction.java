package ru.masnaviev.explore.model;

public enum StateAction { //TODO отдельный класс для PUBLISH_EVENT, REJECT_EVENT т.к это только для админа
    SEND_TO_REVIEW, CANCEL_REVIEW, PUBLISH_EVENT, REJECT_EVENT
}
