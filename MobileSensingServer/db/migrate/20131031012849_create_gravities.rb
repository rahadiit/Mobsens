class CreateGravities < ActiveRecord::Migration
  def change
    create_table :gravities do |t|
      t.references :recording, index: true
      t.datetime :time
      t.float :x
      t.float :y
      t.float :z

      t.timestamps
    end
  end
end
