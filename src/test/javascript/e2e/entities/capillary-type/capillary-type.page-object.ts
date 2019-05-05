import { element, by, ElementFinder } from 'protractor';

export class CapillaryTypeComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-capillary-type div table .btn-danger'));
    title = element.all(by.css('jhi-capillary-type div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CapillaryTypeUpdatePage {
    pageTitle = element(by.id('jhi-capillary-type-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    capillaryNameInput = element(by.id('field_capillaryName'));
    capillaryLengthInput = element(by.id('field_capillaryLength'));
    effectiveLengthInput = element(by.id('field_effectiveLength'));
    innerDiameterInput = element(by.id('field_innerDiameter'));
    outerDiameterInput = element(by.id('field_outerDiameter'));
    electricForceInput = element(by.id('field_electricForce'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setCapillaryNameInput(capillaryName) {
        await this.capillaryNameInput.sendKeys(capillaryName);
    }

    async getCapillaryNameInput() {
        return this.capillaryNameInput.getAttribute('value');
    }

    async setCapillaryLengthInput(capillaryLength) {
        await this.capillaryLengthInput.sendKeys(capillaryLength);
    }

    async getCapillaryLengthInput() {
        return this.capillaryLengthInput.getAttribute('value');
    }

    async setEffectiveLengthInput(effectiveLength) {
        await this.effectiveLengthInput.sendKeys(effectiveLength);
    }

    async getEffectiveLengthInput() {
        return this.effectiveLengthInput.getAttribute('value');
    }

    async setInnerDiameterInput(innerDiameter) {
        await this.innerDiameterInput.sendKeys(innerDiameter);
    }

    async getInnerDiameterInput() {
        return this.innerDiameterInput.getAttribute('value');
    }

    async setOuterDiameterInput(outerDiameter) {
        await this.outerDiameterInput.sendKeys(outerDiameter);
    }

    async getOuterDiameterInput() {
        return this.outerDiameterInput.getAttribute('value');
    }

    async setElectricForceInput(electricForce) {
        await this.electricForceInput.sendKeys(electricForce);
    }

    async getElectricForceInput() {
        return this.electricForceInput.getAttribute('value');
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class CapillaryTypeDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-capillaryType-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-capillaryType'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
