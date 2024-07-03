/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import { ComponentExtension, useComponents } from '@eclipse-sirius/sirius-components-core';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { HelpTooltip } from './HelpTooltip';
import { PropertySectionLabelDecoratorProps, PropertySectionLabelProps } from './PropertySectionLabel.types';
import { propertySectionLabelDecoratorExtensionPoint } from './PropertySectionLabelExtensionPoints';

const usePropertySectionLabelStyles = makeStyles((theme) => ({
  propertySectionLabel: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    gap: theme.spacing(1),
  },
  typography: {
    lineHeight: '1.5',
  },
  decorators: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    gap: theme.spacing(1),
  },
}));

export const PropertySectionLabel = ({ editingContextId, formId, widget }: PropertySectionLabelProps) => {
  const classes = usePropertySectionLabelStyles();

  const propertySectionPanelDecorators: ComponentExtension<PropertySectionLabelDecoratorProps>[] = useComponents(
    propertySectionLabelDecoratorExtensionPoint
  );
  const decorators = (
    <div className={classes.decorators}>
      {widget.hasHelpText ? (
        <HelpTooltip editingContextId={editingContextId} formId={formId} widgetId={widget.id} />
      ) : null}
      {propertySectionPanelDecorators.map(({ Component: PropertySectionPanelDecorator }, index) => (
        <PropertySectionPanelDecorator
          editingContextId={editingContextId}
          formId={formId}
          widget={widget}
          key={index}
        />
      ))}
    </div>
  );

  return (
    <div className={classes.propertySectionLabel}>
      {!!widget.label ? (
        <Typography className={classes.typography} variant="subtitle2">
          {widget.label}
        </Typography>
      ) : null}
      {decorators}
    </div>
  );
};
