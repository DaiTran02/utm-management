package com.ngn.utm.manager.views.pfsenses.rule.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.ngn.utm.manager.api.pfsenses.ApiResultPfsenseResponse;
import com.ngn.utm.manager.api.pfsenses.rule.ApiFirewallRuleInputModel;
import com.ngn.utm.manager.api.pfsenses.rule.ApiFirewallRuleService;
import com.ngn.utm.manager.api.pfsenses.utm_interface.ApiInterfaceService;
import com.ngn.utm.manager.api.pfsenses.utm_interface.ApiStatusInterfaceModel;
import com.ngn.utm.manager.utils.commons.ButtonTemplate;
import com.ngn.utm.manager.utils.commons.HeaderComponent;
import com.ngn.utm.manager.utils.commons.VerticalLayoutTemplate;
import com.ngn.utm.manager.views.pfsenses.rule.enums.ProtocolEnum;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class FirewallRuleEditForm extends VerticalLayoutTemplate{
	private static final long serialVersionUID = 1L;
	private ApiInterfaceService apiInterfaceService;
	private ApiFirewallRuleService apiFirewallRuleService;

	private VerticalLayout vLayoutGeneral = new VerticalLayout();

	//Firewall edit
	private ComboBox<Pair<String, String>> cmbAction = new ComboBox<Pair<String,String>>("Action");
	private ComboBox<Pair<String, String>> cmbInterface = new ComboBox<Pair<String,String>>("Interface");
	private ComboBox<Pair<String, String>> cmbAddressFamily = new ComboBox<Pair<String,String>>("Address Family");
	private ComboBox<Pair<String, String>> cmbProtocol = new ComboBox<Pair<String,String>>("Protocol");
	private Checkbox cbDisableRule = new Checkbox("Disable this rule");

	//Source
	private Checkbox cbInvertmatchSource = new Checkbox("Invert match");
	private ComboBox<Pair<String, String>> cmbNetwork = new ComboBox<Pair<String,String>>();
	private TextField txtSourceAddress = new TextField();
	private ComboBox<Pair<String, String>> cmbPort = new ComboBox<>(); 
	private MultiSelectComboBox<Pair<String, String>> cmbICMPSubTypes = new MultiSelectComboBox<Pair<String,String>>("ICMP Subtypes");
	private Details detailsSource = new Details("Display Advanced");
	private ComboBox<Pair<String, String>> cmbFromSourcePortRange = new ComboBox<Pair<String,String>>("From");
	private TextField txtFromSourcePortRange = new TextField("Custom");
	private ComboBox<Pair<String, String>> cmbToSourcePortRange = new ComboBox<Pair<String,String>>("To");
	private TextField txtToSourcePortRange = new TextField("Custom");


	//Destination
	private Checkbox cbInvertmatchDestination = new Checkbox("Invert match");
	private ComboBox<Pair<String, String>> cmbNetworkDestination = new ComboBox<Pair<String,String>>();
	private TextField txtNetworkDestination = new TextField();
	private ComboBox<Pair<String, String>> cmbPortDesti = new ComboBox<Pair<String,String>>();
	private VerticalLayout vLayoutDestinationPortRange = new VerticalLayout();
	private ComboBox<Pair<String, String>> cmbFromDestinationPortRange = new ComboBox<Pair<String,String>>("From");
	private TextField txtFromDestination = new TextField("Custom");
	private ComboBox<Pair<String, String>> cmbToDestinationPortRange = new ComboBox<Pair<String,String>>("To");
	private TextField txtToDestination = new TextField("Custom");

	//Extra Options
	private Checkbox cbLog = new Checkbox("Log packets that are handled by this rule");
	private TextField txtDesrExtra = new TextField("Description");

	private ButtonTemplate btnSave = new ButtonTemplate("Save",new SvgIcon(LineAwesomeIconUrl.SAVE));

	private VerticalLayout vLayoutFirewallEdit = new VerticalLayout();
	private VerticalLayout vlayoutSource = new VerticalLayout();
	private VerticalLayout vLayoutDestination = new VerticalLayout();
	private VerticalLayout vLayoutExtraOption = new VerticalLayout();
	private VerticalLayout vLayoutDetailSource = new VerticalLayout();

	//source
	public FirewallRuleEditForm(ApiFirewallRuleService apiFirewallRuleService,ApiInterfaceService apiInterfaceService) {
		this.apiFirewallRuleService = apiFirewallRuleService;
		this.apiInterfaceService = apiInterfaceService;
		loadData();
		builLayout();
		configComponent();
	}

	public void builLayout() {
		this.setSizeFull();
		this.add(vLayoutGeneral);
		createLayout();
	}

	public void configComponent() {
		cmbProtocol.addValueChangeListener(e->{
			layoutEditFireWallRule();
			initLayoutSource();
			initDestinationLayout();
		});

		cmbFromSourcePortRange.addValueChangeListener(e->{
			if(cmbFromSourcePortRange.getValue().getKey().isEmpty()) {
				txtFromSourcePortRange.setReadOnly(false);
				txtFromSourcePortRange.focus();
			}else {
				txtFromSourcePortRange.setReadOnly(true);
			}
			
			cmbToSourcePortRange.setValue(e.getValue());
			
		});
		
		cmbToSourcePortRange.addValueChangeListener(e->{
			if(cmbToSourcePortRange.getValue().getKey().isEmpty()) {
				txtToSourcePortRange.setReadOnly(false);
				txtToSourcePortRange.focus();
			}else {
				txtToSourcePortRange.setReadOnly(true);
			}
		});
		
		cmbFromDestinationPortRange.addValueChangeListener(e->{
			if(e.getValue().getKey().isEmpty()) {
				txtFromDestination.setReadOnly(false);
				txtFromDestination.focus();
			}else {
				txtFromDestination.setReadOnly(true);
			}
			cmbToDestinationPortRange.setValue(e.getValue());
		});
		
		cmbToDestinationPortRange.addValueChangeListener(e->{
			if(e.getValue().getKey().isEmpty()) {
				txtToDestination.setReadOnly(false);
			}else {
				txtToDestination.setReadOnly(true);
			}
		});
		
		cmbNetwork.addValueChangeListener(e->{
			if(e.getValue().getKey().equals("single")) {
				txtSourceAddress.setEnabled(true);
			}else if(e.getValue().getKey().equals("network")) {
				txtSourceAddress.setEnabled(true);
				cmbPort.setEnabled(true);
			}else {
				txtSourceAddress.setEnabled(false);
				cmbPort.setEnabled(false);
			}
		});
		
		cmbNetworkDestination.addValueChangeListener(e->{
			if(e.getValue().getKey().equals("single")) {
				txtNetworkDestination.setEnabled(true);
			}else if(e.getValue().getKey().equals("network")) {
				txtNetworkDestination.setEnabled(true);
				cmbPortDesti.setEnabled(true);
			}else {
				txtNetworkDestination.setEnabled(false);
				cmbPortDesti.setEnabled(false);
			}
		});
		
		btnSave.addClickListener(e->{
			saveNewRule();
		});
		
	}

	private void loadData() {
		initAction();
		initAddressFamily();
		initInterface();
		initProtocol();
		initPortSource();
		initCmbNetwork();
		initDestinationLayout();
		initLayoutExtraOptions();
		initPortDestination();
		initCmbDestination();
		initCmbICMPSubTypes();
		initPortRangeFrom();
	}

	@SuppressWarnings("deprecation")
	private void createLayout() {

		btnSave.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		btnSave.setWidth("150px");
		btnSave.getStyle().set("margin-left", "auto").set("margin-right", "10px").set("cursor", "pointer");
		
		txtFromSourcePortRange.setReadOnly(true);
		txtToSourcePortRange.setReadOnly(true);
		
		txtFromDestination.setReadOnly(cmbFromDestinationPortRange.getValue().getKey().isEmpty() ? false : true);
		txtToDestination.setReadOnly(cmbToDestinationPortRange.getValue().getKey().isEmpty() ?  false : true);

		vLayoutGeneral.add(vLayoutFirewallEdit,vlayoutSource,vLayoutDestination,vLayoutExtraOption,btnSave);
		layoutEditFireWallRule();
		initLayoutSource();
		detailsSource.addContent(vLayoutDetailSource);
		detailsSource.setWidthFull();
		detailsSource.getStyle().set("background", "#efefef").set("border-radius", "10px");
	}

	private void layoutEditFireWallRule() {
		vLayoutFirewallEdit.removeAll();
		HeaderComponent headerComponentBlue = new HeaderComponent("Edit Firewall Rule");

		VerticalLayout vLayout = new VerticalLayout();

		cmbAction.setWidthFull();
		cmbAction.setHelperText("Choose what to do with packets that match the criteria specified below.\r\n"
				+ "Hint: the difference between block and reject is that with reject, a packet (TCP RST or ICMP port unreachable for UDP) is returned to the sender, whereas with block the packet is dropped silently. In either case, the original packet is discarded.");


		cmbInterface.setWidthFull();
		cmbInterface.setHelperText("Choose the interface from which packets must come to match this rule.");

		cmbAddressFamily.setWidthFull();
		cmbAddressFamily.setHelperText("Select the Internet Protocol version this rule applies to.");

		cmbProtocol.setWidthFull();
		cmbProtocol.setHelperText("Choose which IP protocol this rule should match.");

		cmbICMPSubTypes.setWidthFull();
		cmbICMPSubTypes.setHelperText("For ICMP rules on IPv4, one or more of these ICMP subtypes may be specified.");


		vLayout.add(cmbAction,cbDisableRule,cmbInterface,cmbAddressFamily,cmbProtocol);

		if(cmbProtocol.getValue().getKey().equals(ProtocolEnum.ICMP.getCode())) {
			vLayout.add(cmbICMPSubTypes);
		}

		headerComponentBlue.addLayout(vLayout);
		headerComponentBlue.addClassName("layout-header-utm");

		vLayoutFirewallEdit.add(headerComponentBlue);
	}

	private void initLayoutSource() {
		vlayoutSource.removeAll();

		VerticalLayout vLayoutt = new VerticalLayout();

		HeaderComponent headerComponentBlue = new HeaderComponent("Source");

		cmbNetwork.setWidthFull();


		HorizontalLayout hLayoutSource = new HorizontalLayout();

		Span sp = new Span("/");
		sp.getStyle().set("margin-top", "5px");

		hLayoutSource.add(txtSourceAddress,sp,cmbPort);

		hLayoutSource.setWidthFull();
		txtSourceAddress.setWidthFull();
		txtSourceAddress.setPlaceholder("Source Address");
		txtSourceAddress.setEnabled(false);
		cmbPort.setEnabled(false);
		
		cmbPort.setEnabled(false);

		vLayoutt.add(cbInvertmatchSource,cmbNetwork,hLayoutSource);

		if(cmbProtocol.getValue().getKey().equals(ProtocolEnum.TCP.getCode())|| cmbProtocol.getValue().getKey().equals(ProtocolEnum.UDP.getCode()) || cmbProtocol.getValue().getKey().equals(ProtocolEnum.TCP_UDP.getCode())) {
			vLayoutt.add(detailsSource);
			initLayoutDetailsSource();
		}


		headerComponentBlue.addClassName("layout-header-utm");
		headerComponentBlue.addLayout(vLayoutt);

		vlayoutSource.add(headerComponentBlue);
	}
	
	private void initLayoutDetailsSource() {
		vLayoutDetailSource.removeAll();
		vLayoutDetailSource.setWidthFull();
		Span spTitle = new Span("Source Port Range");
		spTitle.getStyle().set("font-weight", "600");
		
		HorizontalLayout hLayoutFrom = new HorizontalLayout();
		hLayoutFrom.setWidthFull();
		cmbFromSourcePortRange.setWidthFull();
		txtFromSourcePortRange.setWidthFull();
		hLayoutFrom.add(cmbFromSourcePortRange,txtFromSourcePortRange);
		
		HorizontalLayout hLayoutTo = new HorizontalLayout();
		hLayoutTo.setWidthFull();
		cmbToSourcePortRange.setWidthFull();
		txtToSourcePortRange.setWidthFull();
		hLayoutTo.add(cmbToSourcePortRange,txtToSourcePortRange);
		
		Span spHelp = new Span("Specify the source port or port range for this rule. The \"To\" field may be left empty if only filtering a single port.");
		
		
		vLayoutDetailSource.add(spTitle,hLayoutFrom,hLayoutTo,spHelp);
	}

	private void initDestinationLayout() {
		vLayoutDestination.removeAll();
		VerticalLayout vLayout = new VerticalLayout();
		HeaderComponent headerComponentBlue = new HeaderComponent("Destination");

		HorizontalLayout hLayout = new HorizontalLayout();

		Span sp = new Span("/");
		sp.getStyle().set("margin-top", "5px");

		txtNetworkDestination.setWidthFull();
		cmbNetworkDestination.setWidthFull();

		hLayout.add(txtNetworkDestination,sp,cmbPortDesti);
		vLayout.add(cbInvertmatchDestination,cmbNetworkDestination,hLayout);
		
		txtNetworkDestination.setEnabled(false);
		cmbPortDesti.setEnabled(false);
		
		if(cmbProtocol.getValue().getKey().equals(ProtocolEnum.TCP.getCode())|| cmbProtocol.getValue().getKey().equals(ProtocolEnum.UDP.getCode()) || cmbProtocol.getValue().getKey().equals(ProtocolEnum.TCP_UDP.getCode())) {
			vLayout.add(vLayoutDestinationPortRange);
			initLayoutPortRangeDestination();
		}

		
		vLayout.setWidthFull();
		hLayout.setWidthFull();

		headerComponentBlue.addLayout(vLayout);
		headerComponentBlue.addClassName("layout-header-utm");

		vLayoutDestination.add(headerComponentBlue);

	}
	
	private void initLayoutPortRangeDestination() {
		vLayoutDestinationPortRange.removeAll();
		HorizontalLayout hLayoutFrom = new HorizontalLayout();
		hLayoutFrom.setWidthFull();
		cmbFromDestinationPortRange.setWidthFull();
		txtFromDestination.setWidthFull();
		hLayoutFrom.add(cmbFromDestinationPortRange,txtFromDestination);
		
		HorizontalLayout hLayoutTo = new HorizontalLayout();
		hLayoutTo.setWidthFull();
		cmbToDestinationPortRange.setWidthFull();
		txtToDestination.setWidthFull();
		hLayoutTo.add(cmbToDestinationPortRange,txtToDestination);
		
		Span spTitle = new Span("Destination Port Range");
		
		spTitle.getStyle().set("font-weight", "600");
		
		Span spHelp = new Span("Specify the destination port or port range for this rule. The \"To\" field may be left empty if only filtering a single port.");
		
		vLayoutDestinationPortRange.add(spTitle,hLayoutFrom,hLayoutTo,spHelp);
		
	}

	private void initLayoutExtraOptions() {
		vLayoutExtraOption.removeAll();
		VerticalLayout vLayout = new VerticalLayout();
		HeaderComponent headerComponentBlue = new HeaderComponent("Extra Options");

		Span spTitleLog = new Span("Hint: the firewall has limited local log space. Don't turn on logging for everything. If doing a lot of logging, consider using a remote syslog server.");

		txtDesrExtra.setWidthFull();
		txtDesrExtra.setHelperText("A description may be entered here for administrative reference. A maximum of 52 characters will be used in the ruleset and displayed in the firewall log.");

		vLayout.add(cbLog,spTitleLog,txtDesrExtra);

		headerComponentBlue.addClassName("layout-header-utm");
		headerComponentBlue.addLayout(vLayout);

		vLayoutExtraOption.add(headerComponentBlue);
	}

	private void initAction() {
		List<Pair<String, String>> listAction = new ArrayList<Pair<String,String>>();
		listAction.add(Pair.of("pass","Pass"));
		listAction.add(Pair.of("block","Block"));
		listAction.add(Pair.of("reject","Reject"));

		cmbAction.setItems(listAction);
		cmbAction.setItemLabelGenerator(Pair::getValue);
		cmbAction.setValue(listAction.get(0));
	}

	private void initInterface() {
		List<Pair<String, String>> listInterface = new ArrayList<Pair<String,String>>();
		try {
			List<ApiStatusInterfaceModel> listInterfaceModels = apiInterfaceService.readInterfaceStatus().getData();
			listInterfaceModels.forEach(model->{
				listInterface.add(Pair.of(model.getName(),model.getDescr()));
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		cmbInterface.setItems(listInterface);
		cmbInterface.setItemLabelGenerator(Pair::getRight);
		if(!listInterface.isEmpty()) {
			cmbInterface.setValue(listInterface.get(0));
		}

	}

	private void initAddressFamily() {
		List<Pair<String, String>> listAddress = new ArrayList<Pair<String,String>>();
		listAddress.add(Pair.of("inet","IPv4"));
		listAddress.add(Pair.of("inet6","IPv6"));
		listAddress.add(Pair.of("inet46","IPv4+6"));
		cmbAddressFamily.setItems(listAddress);
		cmbAddressFamily.setItemLabelGenerator(Pair::getRight);
		cmbAddressFamily.setValue(listAddress.get(0));
	}

	private void initProtocol() {
		List<Pair<String, String>> listProtocol = new ArrayList<Pair<String,String>>();
		listProtocol.add(Pair.of("any","Any"));
		listProtocol.add(Pair.of("tcp","TCP"));
		listProtocol.add(Pair.of("udp","UDP"));
		listProtocol.add(Pair.of("tcp/udp","TCP/UDP"));
		listProtocol.add(Pair.of("icmp","ICMP"));
		listProtocol.add(Pair.of("esp","ESP"));
		listProtocol.add(Pair.of("ah","AH"));
		listProtocol.add(Pair.of("gre","GRE"));
		listProtocol.add(Pair.of("etherip","EoIP"));
		listProtocol.add(Pair.of("ipv6","IPV6"));
		listProtocol.add(Pair.of("igmp","IGMP"));
		listProtocol.add(Pair.of("pim","PIM"));
		listProtocol.add(Pair.of("ospf","OSPF"));
		listProtocol.add(Pair.of("sctp","SCTP"));
		listProtocol.add(Pair.of("carp","CARP"));
		listProtocol.add(Pair.of("pfsync","PFSYNC"));

		cmbProtocol.setItems(listProtocol);
		cmbProtocol.setItemLabelGenerator(Pair::getRight);
		cmbProtocol.setValue(listProtocol.get(0));
	}

	private void initCmbICMPSubTypes() {
		List<Pair<String, String>> listICMPSubTypes = new ArrayList<Pair<String,String>>();
		listICMPSubTypes.add(Pair.of("any","any"));
		listICMPSubTypes.add(Pair.of("althost","Alternate Host"));
		listICMPSubTypes.add(Pair.of("dataconv","Datagram conversion error"));
		listICMPSubTypes.add(Pair.of("echorep","Echo reply"));
		listICMPSubTypes.add(Pair.of("echoreq","Echo request"));
		listICMPSubTypes.add(Pair.of("inforep","Information reply"));
		listICMPSubTypes.add(Pair.of("inforeq","Information request"));
		listICMPSubTypes.add(Pair.of("ipv6-here","IPv6 I-am-here"));
		listICMPSubTypes.add(Pair.of("ipv6-where","IPv6 where-are-you"));
		listICMPSubTypes.add(Pair.of("maskrep","Address mask reply"));
		listICMPSubTypes.add(Pair.of("maskreq","Address mask request"));
		listICMPSubTypes.add(Pair.of("mobredir","Mobile host redirect"));
		listICMPSubTypes.add(Pair.of("mobregrep","Mobile registration reply"));
		listICMPSubTypes.add(Pair.of("mobregreq","Mobile registration request"));
		listICMPSubTypes.add(Pair.of("paramprob","Parameter problem (invalid IP header)"));
		listICMPSubTypes.add(Pair.of("photuris","Photuris"));
		listICMPSubTypes.add(Pair.of("redir","Redirect"));
		listICMPSubTypes.add(Pair.of("routeradv","Router advertisement"));
		listICMPSubTypes.add(Pair.of("routersol","Router solicitation"));
		listICMPSubTypes.add(Pair.of("skip","SKIP"));
		listICMPSubTypes.add(Pair.of("squench","Source quench"));
		listICMPSubTypes.add(Pair.of("timerep","Timestamp reply"));
		listICMPSubTypes.add(Pair.of("timereq","Timestamp"));
		listICMPSubTypes.add(Pair.of("timex","Time exceeded"));
		listICMPSubTypes.add(Pair.of("trace","Traceroute"));
		listICMPSubTypes.add(Pair.of("unreach","Destination unreachable"));

		cmbICMPSubTypes.setItems(listICMPSubTypes);
		cmbICMPSubTypes.setItemLabelGenerator(Pair::getValue);

	}

	private void initCmbNetwork() {
		List<Pair<String, String>> listNetwork = new ArrayList<Pair<String,String>>();
		listNetwork.add(Pair.of("any","any"));
		listNetwork.add(Pair.of("single","Single host or alias"));
		listNetwork.add(Pair.of("network","Network"));
		listNetwork.add(Pair.of("pppoe","PPPoE clients"));
		listNetwork.add(Pair.of("l2tp","L2TP clients"));
		listNetwork.add(Pair.of("wan","WANCL net"));
		listNetwork.add(Pair.of("wanip","WANCL address"));

		cmbNetwork.setItems(listNetwork);
		cmbNetwork.setItemLabelGenerator(Pair::getValue);
		cmbNetwork.setValue(listNetwork.get(0));

	}

	private void initPortSource() {
		List<Pair<String, String>> listPort = new ArrayList<>();
		listPort.add(Pair.of(null,""));
		for(int i = 1 ; i <= 32 ; i++) {
			listPort.add(Pair.of(String.valueOf(i),String.valueOf(i)));
		}

		cmbPort.setItems(listPort);
		cmbPort.setItemLabelGenerator(Pair::getValue);
		cmbPort.setValue(listPort.get(0));

	}

	private void initPortDestination() {
		List<Pair<String, String>> listPort = new ArrayList<>();
		listPort.add(Pair.of(null,""));
		for(int i = 1 ; i <= 32 ; i++) {
			listPort.add(Pair.of(String.valueOf(i),String.valueOf(i)));
		}

		cmbPortDesti.setItems(listPort);
		cmbPortDesti.setItemLabelGenerator(Pair::getValue);
		cmbPortDesti.setValue(listPort.get(0));
	}


	private void initCmbDestination() {
		List<Pair<String, String>> listNetwork = new ArrayList<Pair<String,String>>();
		listNetwork.add(Pair.of("any","any"));
		listNetwork.add(Pair.of("single","Single host or alias"));
		listNetwork.add(Pair.of("network","Network"));
		listNetwork.add(Pair.of("pppoe","PPPoE clients"));
		listNetwork.add(Pair.of("l2tp","L2TP clients"));
		listNetwork.add(Pair.of("wan","WANCL net"));
		listNetwork.add(Pair.of("wanip","WANCL address"));

		cmbNetworkDestination.setItems(listNetwork);
		cmbNetworkDestination.setItemLabelGenerator(Pair::getValue);
		cmbNetworkDestination.setValue(listNetwork.get(0));
	}
	
	private void initPortRangeFrom() {
		List<Pair<String, String>> listPort = new ArrayList<Pair<String, String>>();
		listPort.add(Pair.of("", "(other)"));
		listPort.add(Pair.of("any", "any"));
		listPort.add(Pair.of("179", "BGP (179)"));
		listPort.add(Pair.of("5999", "CVSup (5999)"));
		listPort.add(Pair.of("53", "DNS (53)"));
		listPort.add(Pair.of("853", "DNS over TLS (853)"));
		listPort.add(Pair.of("21", "FTP (21)"));
		listPort.add(Pair.of("3000", "HBCI (3000)"));
		listPort.add(Pair.of("80", "HTTP (80)"));
		listPort.add(Pair.of("443", "HTTPS (443)"));
		listPort.add(Pair.of("5190", "ICQ (5190)"));
		listPort.add(Pair.of("113", "IDENT/AUTH (113)"));
		listPort.add(Pair.of("143", "IMAP (143)"));
		listPort.add(Pair.of("993", "IMAP/S (993)"));
		listPort.add(Pair.of("4500", "IPsec NAT-T (4500)"));
		listPort.add(Pair.of("500", "ISAKMP (500)"));
		listPort.add(Pair.of("1701", "L2TP (1701)"));
		listPort.add(Pair.of("389", "LDAP (389)"));
		listPort.add(Pair.of("636", "LDAP/S (636)"));
		listPort.add(Pair.of("1755", "MMS/TCP (1755)"));
		listPort.add(Pair.of("7000", "MMS/UDP (7000)"));
		listPort.add(Pair.of("445", "MS DS (445)"));
		listPort.add(Pair.of("3389", "MS RDP (3389)"));
		listPort.add(Pair.of("1512", "MS WINS (1512)"));
		listPort.add(Pair.of("1863", "MSN (1863)"));
		listPort.add(Pair.of("119", "NNTP (119)"));
		listPort.add(Pair.of("123", "NTP (123)"));
		listPort.add(Pair.of("138", "NetBIOS-DGM (138)"));
		listPort.add(Pair.of("137", "NetBIOS-NS (137)"));
		listPort.add(Pair.of("139", "NetBIOS-SSN (139)"));
		listPort.add(Pair.of("1194", "OpenVPN (1194)"));
		listPort.add(Pair.of("110", "POP3 (110)"));
		listPort.add(Pair.of("995", "POP3/S (995)"));
		listPort.add(Pair.of("1723", "PPTP (1723)"));
		listPort.add(Pair.of("1812", "RADIUS (1812)"));
		listPort.add(Pair.of("1813", "RADIUS accounting (1813)"));
		listPort.add(Pair.of("5004", "RTP (5004)"));
		listPort.add(Pair.of("5060", "SIP (5060)"));
		listPort.add(Pair.of("25", "SMTP (25)"));
		listPort.add(Pair.of("465", "SMTP/S (465)"));
		listPort.add(Pair.of("161", "SNMP (161)"));
		listPort.add(Pair.of("162", "SNMP-Trap (162)"));
		listPort.add(Pair.of("22", "SSH (22)"));
		listPort.add(Pair.of("3478", "STUN (3478)"));
		listPort.add(Pair.of("587", "SUBMISSION (587)"));
		listPort.add(Pair.of("514", "Syslog (514)"));
		listPort.add(Pair.of("3544", "Teredo (3544)"));
		listPort.add(Pair.of("23", "Telnet (23)"));
		listPort.add(Pair.of("69", "TFTP (69)"));
		listPort.add(Pair.of("5900", "VNC (5900)"));
		
		cmbFromSourcePortRange.setItems(listPort);
		cmbFromSourcePortRange.setItemLabelGenerator(Pair::getValue);
		cmbFromSourcePortRange.setValue(listPort.get(0));
		
		cmbToSourcePortRange.setItems(listPort);
		cmbToSourcePortRange.setItemLabelGenerator(Pair::getValue);
		cmbFromSourcePortRange.setValue(cmbToSourcePortRange.getValue());
		
		cmbFromDestinationPortRange.setItems(listPort);
		cmbFromDestinationPortRange.setItemLabelGenerator(Pair::getValue);
		cmbFromDestinationPortRange.setValue(listPort.get(0));
		
		cmbToDestinationPortRange.setItems(listPort);
		cmbToDestinationPortRange.setItemLabelGenerator(Pair::getValue);
		cmbToDestinationPortRange.setValue(listPort.get(0));
	}
	
	private void saveNewRule() {
		ApiFirewallRuleInputModel createUtmFirewallRuleModel = new ApiFirewallRuleInputModel();
		
//		createUtmFirewallRuleModel.setAckqueue("");  // Hàng đợi ACK, có thể để trống nếu không sử dụng
		createUtmFirewallRuleModel.setApply(true);  // Áp dụng quy tắc ngay lập tức
//		createUtmFirewallRuleModel.setDefaultqueue("");  // Hàng đợi mặc định, có thể để trống
		createUtmFirewallRuleModel.setDescr(txtDesrExtra.getValue());  // Mô tả quy tắc

		createUtmFirewallRuleModel.setDirection("in");  // Hướng gói tin (in/out/any)
		createUtmFirewallRuleModel.setDisabled(cbDisableRule.getValue());  // Quy tắc không bị vô hiệu hóa
		
		// Đích
		createUtmFirewallRuleModel.setDst(cmbNetworkDestination.getValue().getKey());  // Địa chỉ đích, ví dụ: "any" hoặc "192.168.1.0/24"
		createUtmFirewallRuleModel.setDstport(txtNetworkDestination.getValue());  // Cổng đích, có thể để trống
		createUtmFirewallRuleModel.setFloating(false);  // Quy tắc không phải Floating Rule
		createUtmFirewallRuleModel.setGateway("default");  // Gateway, ví dụ: "default"


		// Giao diện mạng
		List<String> interfaceList = new ArrayList<>();
		interfaceList.add(cmbInterface.getValue().getKey());  // Ví dụ: WAN
		createUtmFirewallRuleModel.setMyinterface(interfaceList);

		// Giao thức (ICMP trong trường hợp này)
		createUtmFirewallRuleModel.setProtocol(cmbProtocol.getValue().getKey());
		// Loại ICMP (nếu giao thức là ICMP)
		
		if(cmbProtocol.getValue().getKey().equals("icmp")) {
			List<String> icmpTypeList = new ArrayList<>();
//			icmpTypeList.add("echoreq");
			
			cmbICMPSubTypes.getSelectedItems().forEach(model->{
				icmpTypeList.add(model.getKey());
			});
			
			createUtmFirewallRuleModel.setIcmptype(icmpTypeList);
		}

		// Ghi log
		createUtmFirewallRuleModel.setLog(cbLog.getValue());

		// Traffic shaping (nếu có thể để trống)
//		createUtmFirewallRuleModel.setPdnpipe(" ");
//		createUtmFirewallRuleModel.setDnpipe(" ");  // Traffic shaping (nếu có thể để trống)

		// Quy tắc nhanh (quick rule)
		createUtmFirewallRuleModel.setQuick(true);  // Nếu cần áp dụng nhanh

//		createUtmFirewallRuleModel.setSched("test");  // Lịch trình, có thể để trống nếu không sử dụng
		createUtmFirewallRuleModel.setSrc(cmbNetwork.getValue().getKey());  // Địa chỉ nguồn
		createUtmFirewallRuleModel.setSrcport(txtSourceAddress.getValue());  // Cổng nguồn, có thể để trống
//		createUtmFirewallRuleModel.setStatetype("none");  // Trạng thái kết nối, ví dụ: "none", "keep state"

		// Cờ TCP (nếu có thể để trống)
//		createUtmFirewallRuleModel.setTcpflags_any(false);  // Không sử dụng cờ TCP đặc biệt
//		createUtmFirewallRuleModel.setTcpflags1(new ArrayList<>());  // Cờ TCP1, có thể để trống
//		createUtmFirewallRuleModel.setTcpflags2(new ArrayList<>());  // Cờ TCP2, có thể để trống

		createUtmFirewallRuleModel.setTop(false);  // Quy tắc không phải ưu tiên

		// Loại quy tắc (block, pass, reject)
		createUtmFirewallRuleModel.setType(cmbAction.getValue().getKey());
		createUtmFirewallRuleModel.setIpprotocol("inet");
		
		try {
			ApiResultPfsenseResponse<Object> save = apiFirewallRuleService.createFirewallRule(createUtmFirewallRuleModel);
			System.out.println(save);
			if(save.getCode() == 200) {
				fireEvent(new ClickEvent(this, false));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
