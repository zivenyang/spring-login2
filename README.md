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
