# spring-login2

```java
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 10000; i++) {
                IVerifyCodeGen iVerifyCodeGen = new SimpleCharVerifyCodeGenImpl();
                VerifyCode verifyCode = null;
                verifyCode = iVerifyCodeGen.generate(80, 28);
                String code = verifyCode.getCode();
                byte[] img = verifyCode.getImgBytes();
                int len;
                File sf=new File("D:\\Workspace\\learning\\java\\jacoco-multiple-modules-demo-master\\captcha\\captcha2\\outputimg");
                if(!sf.exists()){
                    sf.mkdirs();
                }
                OutputStream os = new FileOutputStream(sf.getPath()+"\\"+code+".jpeg");
                os.write(img);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
```

```python
import time
import os

# 导入包
import muggle_ocr

# ModelType.Captcha 可识别4-6位验证码
sdk = muggle_ocr.SDK(model_type=muggle_ocr.ModelType.Captcha)
path = "/root/test" #文件夹目录
files= os.listdir(path) #得到文件夹下的所有文件名称
totle_example=0
success_example=0
error_example=0
error_set=[]
for file in files: #遍历文件夹
    (_, all_filename) = os.path.split(file)
    (filename, ext) = all_filename.split(".")
    if ext == "jpeg":
       totle_example += 1
       target = filename.lower()
       with open(os.path.join(path, file), "rb") as f:
           b = f.read()
           st = time.time()
           predict = sdk.predict(image_bytes=b)
       if target == predict:
           success_example += 1
       else:
           error_example += 1
           error_set.append([target, predict])
print("totle:%d, success:%d, err:%d" % (totle_example, success_example, error_example))
print(error_set)
```





======================

# 使用tensorflow进行联邦学习  

## 下载tensorflow_federated模块，此处使用较稳定的0.13.1版本，可根据实际情况选择版本  
```shell
pip3 install --upgrade tensorflow_federated==0.13.1 -i http://pypi.douban.com/simple --trusted-host pypi.douban.com
```
## 引入所需模块
```python
import tensorflow as tf
import tensorflow_federated as tff
```

## 下载手写体数字识别数据集
```python
source, _ = tff.simulation.datasets.emnist.load_data()
def client_data(n):
  return source.create_tf_dataset_for_client(source.client_ids[n]).map(
      lambda e: (tf.reshape(e['pixels'], [-1]), e['label'])
  ).repeat(10).batch(20)
  # Pick a subset of client devices to participate in training.
train_data = [client_data(n) for n in range(3)]
# Grab a single batch of data so that TFF knows what data looks like.
sample_batch = tf.nest.map_structure(
    lambda x: x.numpy(), iter(train_data[0]).next())
```

## 定义模型
```python
# Wrap a Keras model for use with TFF.
def model_fn():
  model = tf.keras.models.Sequential([
      tf.keras.layers.Dense(10, tf.nn.softmax, input_shape=(784,),
                            kernel_initializer='zeros')
  ])
  return tff.learning.from_keras_model(
      model,
      dummy_batch=sample_batch,
      loss=tf.keras.losses.SparseCategoricalCrossentropy(),
      metrics=[tf.keras.metrics.SparseCategoricalAccuracy()])
```

## 训练模型
```python
# Simulate a few rounds of training with the selected client devices.
trainer = tff.learning.build_federated_averaging_process(
  model_fn,
  client_optimizer_fn=lambda: tf.keras.optimizers.SGD(0.1))
state = trainer.initialize()
for _ in range(20):
  state, metrics = trainer.next(state, train_data)
  print (metrics.loss)
```

