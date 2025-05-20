#!/bin/bash
sudo -u ec2-user /home/ec2-user/app/deploy/deploy.sh > /home/ec2-user/app/deploy/deploy.log 2>&1 < /dev/null &
#sudo -u ec2-user /home/ec2-user/app/deploy/deploy.sh > /dev/null 2> /dev/null < /dev/null &