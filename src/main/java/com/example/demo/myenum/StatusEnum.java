package com.example.demo.myenum;

public enum StatusEnum {
	Enable(1),Deleted(0);
	
	private int value;
	public int getValue() {
		return this.value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
    //构造方法必须是private或者默认
    private StatusEnum(int value) {
        this.value = value;
    }

    public StatusEnum valueOf(int value) {
        switch (value) {
        case 0:
            return StatusEnum.Deleted;
        case 1:
            return StatusEnum.Enable;
        default:
            return null;
        }
    }
}
