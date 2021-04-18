package gundam.pojo;

public class Test {
    private Integer id;
    private String darkstarline;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDarkstarline() {
        return darkstarline;
    }

    public void setDarkstarline(String darkstarline) {
        this.darkstarline = darkstarline;
    }

    @Override
    public String toString() {
        return "test{" +
                "id=" + id +
                ", darkstarline='" + darkstarline + '\'' +
                '}';
    }
}
