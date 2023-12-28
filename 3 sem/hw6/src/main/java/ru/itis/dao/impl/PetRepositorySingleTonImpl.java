package ru.itis.dao.impl;

import ru.itis.dao.PetRepository;
import ru.itis.dao.SimpleDataSourceSingleton;
import ru.itis.models.Account;
import ru.itis.models.Pet;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class PetRepositorySingleTonImpl implements PetRepository {

    private final static String SQL_GET_BY_ID = "select * from pet where id = ?";
    private final static String SQL_INSERT_PET = "insert into pet(name, age, height, color, sex, account_id)" +
            " values(?, ?, ?, ?, ?, ?)";
    private final static String SQL_GET_ALL_PETS = "select p.id, p.name, p.age, p.height, p.color, p.sex, a.name as account_name\n" +
            "from pet p\n" +
            "join account a\n" +
            "on p.account_id = a.id";

    private final static String SQL_UPDATE_PET = "update pet\n" +
            "set name = ?, age = ?, height = ?, color = ?, sex = ?\n" +
            "where id = ?";

    private final static String SQL_DELETE_PET_BY_ID = "delete from pet where id = ?";
    private final static String SQL_GET_ALL_PETS_BY_ACCOUNT_NAME  = "select p.id, p.name, p.age, p.height, p.color, p.sex \n" +
            "from pet p\n" +
            "where p.account_id = (select id from account where name = ?)";
    private static PetRepositorySingleTonImpl instance;
    private final DataSource dataSource;


    private PetRepositorySingleTonImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public static PetRepositorySingleTonImpl getInstance(){
        if(instance == null){
            instance = new PetRepositorySingleTonImpl(SimpleDataSourceSingleton.getInstance());
        }
        return instance;
    }
    private Function<ResultSet, Pet> mapWithAccountName = row -> {
      try {
          Long id = row.getLong("id");
          String name = row.getString("name");
          Integer age = row.getInt("age");
          Double height = row.getDouble("height");
          String color = row.getString("color");
          String sex = row.getString("sex");
          String accountName = row.getString("account_name");

          Pet pet = Pet.builder()
                  .id(id)
                  .name(name)
                  .age(age)
                  .height(height)
                  .color(color)
                  .account(Account.builder().name(accountName).build())
                  .build();


          if(sex.toLowerCase().contains("female") || sex.toLowerCase().contains("male")){
              pet.setSex(stringSexToEnum.apply(sex));
          }

          return pet;

      } catch (SQLException e){
          throw new IllegalArgumentException(e.getMessage());
      }
    };

    private Function<ResultSet, Pet> map = row -> {
        try {
            Long id = row.getLong("id");
            String name = row.getString("name");
            Integer age = row.getInt("age");
            Double height = row.getDouble("height");
            String color = row.getString("color");
            String sex = row.getString("sex");

            Pet pet = Pet.builder()
                    .id(id)
                    .name(name)
                    .age(age)
                    .height(height)
                    .color(color)
                    .build();


            if(sex.toLowerCase().contains("female") || sex.toLowerCase().contains("male")){
                pet.setSex(stringSexToEnum.apply(sex));
            }

            return pet;

        } catch (SQLException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    };

    private static Function<String, Pet.Sex> stringSexToEnum = str -> {
        if(str.equalsIgnoreCase("female"))
            return Pet.Sex.FEMALE;
        else if(str.equalsIgnoreCase("male"))
            return Pet.Sex.MALE;
        else
            throw new IllegalArgumentException("Not a sex");
    };
    @Override
    public void save(Pet pet) {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection
                    .prepareStatement(SQL_INSERT_PET, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, pet.getName());
            preparedStatement.setInt(2, pet.getAge());
            preparedStatement.setDouble(3, pet.getHeight());
            preparedStatement.setString(4, pet.getColor());
            preparedStatement.setString(5, String.valueOf(pet.getSex()).toLowerCase());
            preparedStatement.setLong(6, pet.getAccount().getId());

            int affectedRowsPet = preparedStatement.executeUpdate();
            if(affectedRowsPet != 1)
                throw new SQLException("Exception in <SavePet>");

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if(generatedKeys.next()){
                pet.setId(generatedKeys.getLong("id"));
            }
        }
        catch (SQLException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public Optional<Pet> getPetById(Long id){
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Pet pet;
            if(resultSet.next())
              pet = map.apply(resultSet);
            else
                throw new IllegalArgumentException();
            return Optional.of(pet);

        } catch (SQLException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public List<Pet> getAllPets() {
        try(Connection connection = dataSource.getConnection()){
            List<Pet> pets = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_PETS);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                pets.add(mapWithAccountName.apply(resultSet));
            }
            return pets;
        } catch (SQLException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public List<Pet> getAllPetsByAccountName(String accountName) {
        try(Connection connection = dataSource.getConnection()) {
            List<Pet> pets = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_PETS_BY_ACCOUNT_NAME);
            preparedStatement.setString(1, accountName);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                pets.add(map.apply(resultSet));
            }
            return pets;
        } catch (SQLException e){
            throw new IllegalArgumentException("Exception <GetAllPetsByAccountName>: " + e.getMessage());
        }
    }

    @Override
    public boolean update(Pet pet) {
        Pet originalPet = getPetById(pet.getId()).orElseThrow(IllegalArgumentException::new);

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_PET);
            //id
            preparedStatement.setLong(6, pet.getId());



            //name
            if(pet.getName() == null)
                preparedStatement.setString(1, originalPet.getName());
            else
                preparedStatement.setString(1, pet.getName());
            //age
            if(pet.getAge() == null)
                preparedStatement.setInt(2, originalPet.getAge());
            else
                preparedStatement.setInt(2, pet.getAge());
            //height
            if(pet.getHeight() == null)
                preparedStatement.setDouble(3, originalPet.getHeight());
            else
                preparedStatement.setDouble(3, pet.getHeight());
            //color
            if(pet.getColor() == null)
                preparedStatement.setString(4, originalPet.getColor());
            else
                preparedStatement.setString(4, pet.getColor());
            //sex
            if(pet.getSex() == null)
                preparedStatement.setString(5, String.valueOf(originalPet.getSex()).toLowerCase());
            else
                preparedStatement.setString(5, String.valueOf(pet.getSex()).toLowerCase());

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows != 1 )
                throw new SQLException("Exception in <UpdatePet>");

            return true;

        } catch (SQLException e){
            throw new IllegalArgumentException("Exception <UpdatePet> : " + e.getMessage());
        }
    }

    @Override
    public boolean delete(Long id) {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_PET_BY_ID);
            preparedStatement.setLong(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows != 1 )
                throw new SQLException("Exception in <DeletePet>");

            return true;
        } catch (SQLException e){
            throw new IllegalArgumentException("Exception <Deletepet> : " + e.getMessage());
        }
    }
}
