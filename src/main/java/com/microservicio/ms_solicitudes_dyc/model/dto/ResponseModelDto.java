package com.microservicio.ms_solicitudes_dyc.model.dto;

public class ResponseModelDto {
    private boolean status;
    private String message;

    public ResponseModelDto(boolean status, String message){
        this.status = status;
        this.message = message;
    }

    public boolean getStatus(){
        return status;
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
