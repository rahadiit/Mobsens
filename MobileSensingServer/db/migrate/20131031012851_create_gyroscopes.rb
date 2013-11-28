class CreateGyroscopes < ActiveRecord::Migration
  def change
    create_table :gyroscopes do |t|
      t.references :recording, index: true
      t.datetime :time
      t.float :rX
      t.float :rY
      t.float :rZ

      t.timestamps
    end
  end
end
