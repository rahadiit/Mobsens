class CreateProximities < ActiveRecord::Migration
  def change
    create_table :proximities do |t|
      t.references :recording, index: true
      t.datetime :time
      t.float :proximity

      t.timestamps
    end
  end
end
