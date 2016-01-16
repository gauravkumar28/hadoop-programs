import static jcuda.driver.JCudaDriver.*;
import java.io.*;


import jcuda.*;
import jcuda.driver.*;

/*
this program demostrats how to use the JCuda driver bindings to load and execute a cuda vector addition kernel
the sample reads a CUDA file ,compiles it to a PTX file using NVCC , loads the PTX
file as a module and executes the kernel function.
*/

public class JCudaVectorMul
{


public static void main(String args[]) throws IOException
{

//Enable exceptions and omit all subsequent error checks
JCudaDriver.setExceptionsEnabled(true);


cuInit(0);
CUdevice device = new CUdevice();
cuDeviceGet(device,0);
CUcontext context=new CUcontext();
cuCtxCreate(context,0,device);

// LOad the ptx file.
CUmodule module=new CUmodule();
cuModuleLoad(module,"JCudaVectorMulKernel.ptx");

//obtain a function pointer to the add function

CUfunction function =new CUfunction();
cuModuleGetFunction(function,module,"mul");
int numElements=100000;
//alloctae and fill the host input data
float hostInputA[]=new float[numElements];
float hostInputB[]=new float[numElements];
for(int i=0;i<numElements;i++)
{
hostInputA[i]=(float)i;
hostInputB[i]=(float)i;
}

//allcoate the device input data and copy the host data to device

CUdeviceptr deviceInputA=new CUdeviceptr();
cuMemAlloc(deviceInputA,numElements*Sizeof.FLOAT);
cuMemcpyHtoD(deviceInputA,Pointer.to(hostInputA),numElements*Sizeof.FLOAT);


CUdeviceptr deviceInputB=new CUdeviceptr();
cuMemAlloc(deviceInputB,numElements*Sizeof.FLOAT);
cuMemcpyHtoD(deviceInputB,Pointer.to(hostInputB),numElements*Sizeof.FLOAT);

//allocate device output memory

CUdeviceptr deviceOutput=new CUdeviceptr();
cuMemAlloc(deviceOutput,numElements*Sizeof.FLOAT);

//set up the kernel parameters : A pointer to an array of pointers which point to the actual values.

Pointer kernelParameters=Pointer.to(Pointer.to(new int[]{numElements}),Pointer.to(deviceInputA),Pointer.to(deviceInputB),Pointer.to(deviceOutput));

//call the kernel function
int blockSizeX=256;
int gridSizex=(int)Math.ceil((double)numElements/blockSizeX);
cuLaunchKernel(function,gridSizex,1,1,blockSizeX,1,1,0,null,kernelParameters,null);
cuCtxSynchronize();
//allocate host output memory and copy the device output to the host

float hostOutput[]=new float[numElements];
cuMemcpyDtoH(Pointer.to(hostOutput),deviceOutput,numElements*Sizeof.FLOAT);

System.out.println(hostOutput[0]);
float result=0.0f;
for(int i=0;i<numElements;i++)
result+=hostOutput[i];
System.out.println(result);
//clean up
cuMemFree(deviceInputA);
cuMemFree(deviceInputB);
cuMemFree(deviceOutput);
}


}













