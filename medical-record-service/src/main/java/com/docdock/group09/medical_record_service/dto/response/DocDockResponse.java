package com.docdock.group09.medical_record_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocDockResponse<T> {
    private String message;
    private int code;
    private T data;
    private Integer page;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElements;
    public static <T> ResponseEntity<DocDockResponse<T>> returnSuccess(String message) {
        DocDockResponse<T> response = DocDockResponse.<T>builder()
                .code(HttpStatus.OK.value())
                .message(message)
                .build();
        return ResponseEntity.ok(response);
    }
    public static <T> ResponseEntity<DocDockResponse<T>> returnSuccess(String message, T data) {
        DocDockResponse<T> response = DocDockResponse.<T>builder()
                .code(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .build();
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<DocDockResponse<T>> returnSuccess(T data) {
        DocDockResponse<T> response = DocDockResponse.<T>builder()
                .code(HttpStatus.OK.value())
                .data(data)
                .build();
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<DocDockResponse<T>> returnSuccess(T data, int code) {
        DocDockResponse<T> response = DocDockResponse.<T>builder()
                .code(HttpStatus.OK.value())
                .data(data)
                .build();
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<DocDockResponse<T>> returnSuccess(T data, int code, String message) {
        DocDockResponse<T> response = DocDockResponse.<T>builder()
                .code(code)
                .data(data)
                .message(message)
                .build();
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<DocDockResponse<List<T>>> returnSuccessPagination(Page<T> data) {
        DocDockResponse<List<T>> response = DocDockResponse.<List<T>>builder()
                .data(data.getContent())
                .code(HttpStatus.OK.value())
                .page(data.getPageable().getPageNumber())
                .pageSize(data.getPageable().getPageSize())
                .totalPages(data.getTotalPages())
                .totalElements(data.getTotalElements())
                .build();
        return ResponseEntity.ok(response);
    }

    public static <T>  ResponseEntity<?> returnError(String msg, int code) {
        DocDockResponse<T> response = DocDockResponse.<T>builder()
                .code(code)
                .message(msg)
                .build();
        return ResponseEntity.ok(response);
    }
}
