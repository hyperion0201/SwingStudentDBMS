package StudentManagement;

public class Student {
    private int id;
    private String name;
    private Float mark;
    private String avatar;
    private String address;
    private String extraInfo;

    public Student() {

    }

    public Student(int id, String name, Float mark, String avatar, String address, String extraInfo) {
        this.id = id;
        this.name = name;
        this.mark = mark;
        this.avatar = avatar;
        this.address = address;
        this.extraInfo = extraInfo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getMark() {
        return mark;
    }
    public String getAddress() {
        return address;
    }
    public String getAvatar() {
        return avatar;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
    public void setMark(Float mark) {
        this.mark = mark;
    }

    public void setName(String name) {
        this.name = name;
    }
}
