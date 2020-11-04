package com.pci.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.pci.entity.MtItem;
import com.pci.entity.MtUser;
import com.pci.entity.TrSalesDetail;
import com.pci.entity.TrSalesOutline;
import com.pci.entity.summary.SalesSummary;
import com.pci.entity.summary.SalesSummaryDate;
import com.pci.repository.CustomerRepository;
import com.pci.repository.ItemRepository;
import com.pci.repository.SalesDetailRepository;
import com.pci.repository.SalesOutlineRepository;
import com.pci.repository.SalesSummaryRepository;
import com.pci.repository.UserRepository;

@Controller
@SessionAttributes("formModel")
public class SaleController {
	
	UserDetails userDetail;
	MtUser user;
	@Autowired
	UserRepository userRepository;	
	@Autowired
	SalesOutlineRepository saleRepository;
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	SalesDetailRepository saleDetailRepository;
	
	@Autowired
	SalesSummaryRepository salesSummaryRepository;
	
	List<TrSalesDetail> salesDetails = new ArrayList<TrSalesDetail>();
	List<TrSalesDetail> removeDetailList = new ArrayList<TrSalesDetail>();
	
	GrantedAuthority grantedAuthority;

	@ModelAttribute(value = "formModel")
	public TrSalesOutline setUpTrSale() {
		return new TrSalesOutline();
	}
	
	@RequestMapping(value = "/sales",method=RequestMethod.GET,params="list")
	public String saveComplete(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		salesDetails = new ArrayList<TrSalesDetail>();
		return "redirect:/salesList";
	}


	@RequestMapping(value = "/salesList",method=RequestMethod.GET)
	public ModelAndView salesList(ModelAndView mav,@AuthenticationPrincipal UserDetails userDetail) {
		this.userDetail=userDetail;
		user=userRepository.findByUserCode(userDetail.getUsername()).get();
		mav.addObject("userName", user.getUserName());
		Collection<? extends GrantedAuthority> auths = userDetail.getAuthorities();
		for(GrantedAuthority ga:auths) {
			if(ga.getAuthority().equals("ROLE_MANAGER")) {
				grantedAuthority=ga;
				mav.addObject("salesList", saleRepository.findAll());
				mav.setViewName("/200manager/210salesList");
				break;
			}else{
				grantedAuthority=ga;
				mav.addObject("salesList", saleRepository.findByMtUser(user.getUserCode()));
				TrSalesOutline sale = new TrSalesOutline();
				mav.addObject("formModel", sale);
				mav.addObject("itemList", itemRepository.findAll());
				mav.addObject("customerList", customerRepository.findAll());
				mav.setViewName("/300staff/310salesList");
				break;
			}
		}
		return mav;
	}
	
	@RequestMapping(value="/salesDetailDisp/{salesId}",method=RequestMethod.GET)
	public ModelAndView salesDetailDisp(
			@PathVariable String salesId,
			ModelAndView mav) {
		user=userRepository.findByUserCode(userDetail.getUsername()).get();
		mav.addObject("userName", user.getUserName());
		mav.addObject("saleOutline", saleRepository.findBySalesId(Long.parseLong(salesId)).get());
		mav.addObject("detailList", saleDetailRepository.findBySalesId(Long.parseLong(salesId)));
		if(grantedAuthority.getAuthority().equals("ROLE_MANAGER")) {
			mav.setViewName("/200manager/211salesDetailList");
		}else {
			mav.setViewName("/300staff/311salesDetailList");
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/salesSummaryByItem",method=RequestMethod.POST)
	public ModelAndView salesSummaryByItem(ModelAndView mav) {
		user=userRepository.findByUserCode(userDetail.getUsername()).get();
		mav.addObject("userName", user.getUserName());
		mav.addObject("salesList", salesSummaryResultConverter(saleDetailRepository.findBySalesSummaryByItem()));
		mav.setViewName("/200manager/212salesSummaryByItem");
		return mav;
	}

	@RequestMapping(value = "/salesSummaryByCustomer",method=RequestMethod.POST)
	public ModelAndView salesSummaryByCustomer(ModelAndView mav) {
		user=userRepository.findByUserCode(userDetail.getUsername()).get();
		mav.addObject("userName", user.getUserName());
		mav.addObject("salesList", salesSummaryResultConverter(saleDetailRepository.findBySalesSummaryByCustomer()));
		mav.setViewName("/200manager/213salesSummaryByCustomer");
		return mav;
	}

	@RequestMapping(value = "/salesSummaryByItemGenre",method=RequestMethod.POST)
	public ModelAndView salesSummaryByItemGenre(ModelAndView mav) {
		user=userRepository.findByUserCode(userDetail.getUsername()).get();
		mav.addObject("userName", user.getUserName());
		mav.addObject("salesList", salesSummaryResultConverter(saleDetailRepository.findBySalesSummaryByItemGenre()));
		mav.setViewName("/200manager/214salesSummaryByItemGenre");
		return mav;
	}

	@RequestMapping(value = "/salesSummaryByStaff",method=RequestMethod.POST)
	public ModelAndView salesSummaryByStaff(ModelAndView mav) {
		user=userRepository.findByUserCode(userDetail.getUsername()).get();
		mav.addObject("userName", user.getUserName());
		mav.addObject("salesList", salesSummaryResultConverter(saleDetailRepository.findBySalesSummaryByStaff()));
		mav.setViewName("/200manager/215salesSummaryByStaff");
		return mav;
	}

	@RequestMapping(value = "/salesSummaryByDate",method=RequestMethod.POST)
	public ModelAndView salesSummaryByDate(ModelAndView mav) {
		user=userRepository.findByUserCode(userDetail.getUsername()).get();
		mav.addObject("userName", user.getUserName());
		mav.addObject("salesList", salesSummaryResultConverterForDate(saleDetailRepository.findBySalesSummaryByDate()));
		mav.setViewName("/200manager/216salesSummaryByDate");
		return mav;
	}
	
	@RequestMapping(value = "/saleCre",method = RequestMethod.POST)
	public ModelAndView SaleCre(ModelAndView mav) {
		user=userRepository.findByUserCode(userDetail.getUsername()).get();
		mav.addObject("userName", user.getUserName());
		TrSalesOutline sale = new TrSalesOutline();
		mav.addObject("formModel", sale);
		mav.addObject("itemList", itemRepository.findAll());
		mav.addObject("customerList", customerRepository.findAll());
		mav.setViewName("/300staff/321salesCre");
		return mav;
	}
	
	@RequestMapping(value = "/saleCreConf",method=RequestMethod.GET)
	public ModelAndView SaleCreConf(
			@ModelAttribute("formModel")TrSalesOutline sale,
			BindingResult result,
			ModelAndView mav) {
		user=userRepository.findByUserCode(userDetail.getUsername()).get();
		mav.addObject("userName", user.getUserName());
		if(!result.hasErrors()) {
			String[] itemCodeArray = sale.getItemCodeArray();
			String[] quantityArray = sale.getQuantityArray();
			if(itemCodeArray!=null) {
				for(int i=0;i<itemCodeArray.length;i++) {
					int qty=Integer.parseInt(quantityArray[i]);
					if(qty!=0) {
						MtItem item = itemRepository.findByItemCode(itemCodeArray[i]).get();
						salesDetails.add(new TrSalesDetail(qty,item.getPrice(),item));
					}
				}
			}
			mav.addObject("salesDetails", salesDetails);
			mav.setViewName("/300staff/322salesCreConf");
		}else {
			mav.addObject("msg", "エラーが発生しました");
			mav.addObject("itemList", itemRepository.findAll());
			mav.addObject("customerList", customerRepository.findAll());
			mav.setViewName("/300staff/321salesCre");
		}
		return mav;
	}
	
	@RequestMapping(value = "/saleRegExe",method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView SaleRegExe(
			@ModelAttribute("formModel")TrSalesOutline sale,
			ModelAndView mav){
		user=userRepository.findByUserCode(userDetail.getUsername()).get();
		mav.addObject("userName", user.getUserName());
		sale.setMtUser(user);
		sale.setSaleDate(java.sql.Date.valueOf(sale.getSalesDateString()));
		for(TrSalesDetail d:salesDetails) {
			sale.addTrSalesDetail(d);
		}
		saleRepository.saveAndFlush(sale);
		sale.setItemCodeArray(null);
		sale.setQuantityArray(null);
		mav.setViewName("redirect:/sales?list");
		return mav;
	}
	
	@RequestMapping(value = "/saleUpd/{salesId}",method=RequestMethod.GET)
	public ModelAndView SaleUpd(
			@PathVariable String salesId,
			ModelAndView mav) {
		user=userRepository.findByUserCode(userDetail.getUsername()).get();
		mav.addObject("userName", user.getUserName());
		mav.addObject("customerList", customerRepository.findAll());
		Long longsalesId=Long.parseLong(salesId);
		
		TrSalesOutline formModel = saleRepository.findBySalesId(longsalesId).get();
		formModel.setSalesDateString(formModel.getSaleDate().toString());
		mav.addObject("formModel", formModel);
		removeDetailList=formModel.getTrSalesDetails();
		List<MtItem> itemList = itemRepository.findAll();		
		for(MtItem i:itemList) {
			Optional<TrSalesDetail> d=saleDetailRepository.findByItemCode(longsalesId,i.getItemCode());
			if(d.isPresent()) {
				i.setQuantity(d.get().getQuantity());
			}else {
				i.setQuantity(0);
			}
		}
		mav.addObject("itemList", itemList);
		mav.setViewName("/300staff/331salesUpd");
		return mav;
	}
	
	@RequestMapping(value = "/saleUpdConf",method=RequestMethod.POST)
	public ModelAndView SaleUpdConf(
			@ModelAttribute("formModel")TrSalesOutline sale,
			BindingResult result,
			ModelAndView mav) {
		user=userRepository.findByUserCode(userDetail.getUsername()).get();
		mav.addObject("userName", user.getUserName());
		if(!result.hasErrors()) {
			String[] itemCodeArray = sale.getItemCodeArray();
			String[] quantityArray = sale.getQuantityArray();
			if(itemCodeArray!=null) {
				for(int i=0;i<itemCodeArray.length;i++) {
					int qty=Integer.parseInt(quantityArray[i]);
					if(qty!=0) {
						MtItem item = itemRepository.findByItemCode(itemCodeArray[i]).get();
						salesDetails.add(new TrSalesDetail(qty,item.getPrice(),item));
					}
				}
			}
			mav.addObject("salesDetails", salesDetails);
			mav.setViewName("/300staff/332salesUpdConf");
		}else {
			mav.addObject("msg", "エラーが発生しました");
			mav.addObject("itemList", itemRepository.findAll());
			mav.addObject("customerList", customerRepository.findAll());
			mav.setViewName("/300staff/331salesUpd");
		}
		return mav;
	}
	
	@RequestMapping(value = "/saleUpdExe",method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView SaleUpdExe(
			@ModelAttribute("formModel")TrSalesOutline sale,
			ModelAndView mav){
		user=userRepository.findByUserCode(userDetail.getUsername()).get();
		mav.addObject("userName", user.getUserName());
		sale.setMtUser(user);
		sale.setSaleDate(java.sql.Date.valueOf(sale.getSalesDateString()));

		sale.getTrSalesDetails().removeAll(removeDetailList);

		for(TrSalesDetail d:salesDetails) {
			sale.addTrSalesDetail(d);
		}
		saleRepository.saveAndFlush(sale);
		sale.setItemCodeArray(null);
		sale.setQuantityArray(null);
		mav.setViewName("redirect:/sales?list");
		return mav;
	}
	
	List<SalesSummary> salesSummaryResultConverter(List<Object[]> result){
		List<SalesSummary> salesList = new ArrayList<>();
		for(Object[] objects : result) {
			salesList.add(new SalesSummary(objects));
		}
		return salesList;
	}
	
	List<SalesSummaryDate> salesSummaryResultConverterForDate(List<Object[]> result){
		List<SalesSummaryDate> salesList = new ArrayList<>();
		for(Object[] objects : result) {
			salesList.add(new SalesSummaryDate(objects));
		}
		return salesList;
	}
}
