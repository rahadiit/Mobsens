class Battery < ActiveRecord::Base
  belongs_to :recording
  include Sensor 
end
