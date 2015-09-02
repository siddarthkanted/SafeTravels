<?xml version="1.0" encoding="utf-8"?>
<serviceModel xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" name="Cloud" generation="1" functional="0" release="0" Id="345570a2-a3f2-4dde-a081-61473d867eca" dslVersion="1.2.0.0" xmlns="http://schemas.microsoft.com/dsltools/RDSM">
  <groups>
    <group name="CloudGroup" generation="1" functional="0" release="0">
      <componentports>
        <inPort name="Service:Endpoint1" protocol="http">
          <inToChannel>
            <lBChannelMoniker name="/Cloud/CloudGroup/LB:Service:Endpoint1" />
          </inToChannel>
        </inPort>
      </componentports>
      <settings>
        <aCS name="Service:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" defaultValue="">
          <maps>
            <mapMoniker name="/Cloud/CloudGroup/MapService:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" />
          </maps>
        </aCS>
        <aCS name="ServiceInstances" defaultValue="[1,1,1]">
          <maps>
            <mapMoniker name="/Cloud/CloudGroup/MapServiceInstances" />
          </maps>
        </aCS>
      </settings>
      <channels>
        <lBChannel name="LB:Service:Endpoint1">
          <toPorts>
            <inPortMoniker name="/Cloud/CloudGroup/Service/Endpoint1" />
          </toPorts>
        </lBChannel>
      </channels>
      <maps>
        <map name="MapService:Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" kind="Identity">
          <setting>
            <aCSMoniker name="/Cloud/CloudGroup/Service/Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" />
          </setting>
        </map>
        <map name="MapServiceInstances" kind="Identity">
          <setting>
            <sCSPolicyIDMoniker name="/Cloud/CloudGroup/ServiceInstances" />
          </setting>
        </map>
      </maps>
      <components>
        <groupHascomponents>
          <role name="Service" generation="1" functional="0" release="0" software="c:\dev\hackathon\SafeTrip\Cloud\csx\Release\roles\Service" entryPoint="base\x64\WaHostBootstrapper.exe" parameters="base\x64\WaIISHost.exe " memIndex="-1" hostingEnvironment="frontendadmin" hostingEnvironmentVersion="2">
            <componentports>
              <inPort name="Endpoint1" protocol="http" portRanges="80" />
            </componentports>
            <settings>
              <aCS name="Microsoft.WindowsAzure.Plugins.Diagnostics.ConnectionString" defaultValue="" />
              <aCS name="__ModelData" defaultValue="&lt;m role=&quot;Service&quot; xmlns=&quot;urn:azure:m:v1&quot;&gt;&lt;r name=&quot;Service&quot;&gt;&lt;e name=&quot;Endpoint1&quot; /&gt;&lt;/r&gt;&lt;/m&gt;" />
            </settings>
            <resourcereferences>
              <resourceReference name="DiagnosticStore" defaultAmount="[4096,4096,4096]" defaultSticky="true" kind="Directory" />
              <resourceReference name="EventStore" defaultAmount="[1000,1000,1000]" defaultSticky="false" kind="LogStore" />
            </resourcereferences>
          </role>
          <sCSPolicy>
            <sCSPolicyIDMoniker name="/Cloud/CloudGroup/ServiceInstances" />
            <sCSPolicyUpdateDomainMoniker name="/Cloud/CloudGroup/ServiceUpgradeDomains" />
            <sCSPolicyFaultDomainMoniker name="/Cloud/CloudGroup/ServiceFaultDomains" />
          </sCSPolicy>
        </groupHascomponents>
      </components>
      <sCSPolicy>
        <sCSPolicyUpdateDomain name="ServiceUpgradeDomains" defaultPolicy="[5,5,5]" />
        <sCSPolicyFaultDomain name="ServiceFaultDomains" defaultPolicy="[2,2,2]" />
        <sCSPolicyID name="ServiceInstances" defaultPolicy="[1,1,1]" />
      </sCSPolicy>
    </group>
  </groups>
  <implements>
    <implementation Id="15453f98-ad6d-47e9-9d2c-d81eb148634c" ref="Microsoft.RedDog.Contract\ServiceContract\CloudContract@ServiceDefinition">
      <interfacereferences>
        <interfaceReference Id="63fbbf69-c589-493c-9464-844b7d538e73" ref="Microsoft.RedDog.Contract\Interface\Service:Endpoint1@ServiceDefinition">
          <inPort>
            <inPortMoniker name="/Cloud/CloudGroup/Service:Endpoint1" />
          </inPort>
        </interfaceReference>
      </interfacereferences>
    </implementation>
  </implements>
</serviceModel>