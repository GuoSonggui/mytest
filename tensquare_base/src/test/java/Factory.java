public class Factory {
    public ClassesDao getClassesDao() {
        ClassesDao cd = new ClassesDaoImpl();
        return cd;
    }
}

interface ClassesDao {
    public String getClassesName();
}

class ClassesDaoImpl implements ClassesDao {

    @Override
    public String getClassesName() {
        System.out.println("AAA");
        return null;
    }
}

class test {
    public static void main(String[] args) {
        Factory f = new Factory();
        f.getClassesDao().getClassesName();
    }
}