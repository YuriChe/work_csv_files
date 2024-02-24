package appCSV.entity;

public class NameCounting {
    private String name;
    private Integer value;

    public NameCounting(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "NameInt{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (this.name.equals(((NameCounting) obj).getName())) return true;
        return false;
    }

    @Override
    public int hashCode() {
        // Ваша логика для вычисления хэш-кода
        return name.hashCode();
    }
}
