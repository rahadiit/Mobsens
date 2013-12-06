class CreateEvents < ActiveRecord::Migration
  def change
    create_table :events do |t|
      t.datetime :time
      t.references :recording, index: true
      t.references :event_type, index: true

      t.timestamps
    end
  end
end
