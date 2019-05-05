import { element, by, ElementFinder } from 'protractor';

export class MethodComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-method div table .btn-danger'));
    title = element.all(by.css('jhi-method div h2#page-heading span')).first();

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

export class MethodUpdatePage {
    pageTitle = element(by.id('jhi-method-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    methodNameInput = element(by.id('field_methodName'));
    frequencyInput = element(by.id('field_frequency'));
    injectionTypeInput = element(by.id('field_injectionType'));
    injectionTimeInput = element(by.id('field_injectionTime'));
    measureValueInput = element(by.id('field_measureValue'));
    unitOfMeasureInput = element(by.id('field_unitOfMeasure'));
    experimentTimeInput = element(by.id('field_experimentTime'));
    currentInput = element(by.id('field_current'));
    capillaryTypeSelect = element(by.id('field_capillaryType'));
    matrixListSelect = element(by.id('field_matrixList'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setMethodNameInput(methodName) {
        await this.methodNameInput.sendKeys(methodName);
    }

    async getMethodNameInput() {
        return this.methodNameInput.getAttribute('value');
    }

    async setFrequencyInput(frequency) {
        await this.frequencyInput.sendKeys(frequency);
    }

    async getFrequencyInput() {
        return this.frequencyInput.getAttribute('value');
    }

    async setInjectionTypeInput(injectionType) {
        await this.injectionTypeInput.sendKeys(injectionType);
    }

    async getInjectionTypeInput() {
        return this.injectionTypeInput.getAttribute('value');
    }

    async setInjectionTimeInput(injectionTime) {
        await this.injectionTimeInput.sendKeys(injectionTime);
    }

    async getInjectionTimeInput() {
        return this.injectionTimeInput.getAttribute('value');
    }

    async setMeasureValueInput(measureValue) {
        await this.measureValueInput.sendKeys(measureValue);
    }

    async getMeasureValueInput() {
        return this.measureValueInput.getAttribute('value');
    }

    async setUnitOfMeasureInput(unitOfMeasure) {
        await this.unitOfMeasureInput.sendKeys(unitOfMeasure);
    }

    async getUnitOfMeasureInput() {
        return this.unitOfMeasureInput.getAttribute('value');
    }

    async setExperimentTimeInput(experimentTime) {
        await this.experimentTimeInput.sendKeys(experimentTime);
    }

    async getExperimentTimeInput() {
        return this.experimentTimeInput.getAttribute('value');
    }

    async setCurrentInput(current) {
        await this.currentInput.sendKeys(current);
    }

    async getCurrentInput() {
        return this.currentInput.getAttribute('value');
    }

    async capillaryTypeSelectLastOption() {
        await this.capillaryTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async capillaryTypeSelectOption(option) {
        await this.capillaryTypeSelect.sendKeys(option);
    }

    getCapillaryTypeSelect(): ElementFinder {
        return this.capillaryTypeSelect;
    }

    async getCapillaryTypeSelectedOption() {
        return this.capillaryTypeSelect.element(by.css('option:checked')).getText();
    }

    async matrixListSelectLastOption() {
        await this.matrixListSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async matrixListSelectOption(option) {
        await this.matrixListSelect.sendKeys(option);
    }

    getMatrixListSelect(): ElementFinder {
        return this.matrixListSelect;
    }

    async getMatrixListSelectedOption() {
        return this.matrixListSelect.element(by.css('option:checked')).getText();
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

export class MethodDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-method-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-method'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
