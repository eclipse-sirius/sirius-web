/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import { makeStyles, Theme } from '@material-ui/core/styles';
import {
  FlexboxContainerPropertySectionProps,
  FlexboxContainerPropertySectionStyleProps,
} from './FlexboxContainerPropertySection.types';
import { PropertySection } from './PropertySection';
import { PropertySectionLabel } from './PropertySectionLabel';

const useFlexboxContainerPropertySectionStyles = makeStyles<Theme, FlexboxContainerPropertySectionStyleProps>(
  (theme) => ({
    containerAndLabel: {
      margin: ({ borderStyle }) => (borderStyle ? theme.spacing(0.5) : 0),
      padding: ({ borderStyle }) => (borderStyle ? theme.spacing(0.5) : 0),
      borderWidth: ({ borderStyle }) => borderStyle?.size || 0,
      borderColor: ({ borderStyle }) => borderStyle?.color || 'transparent',
      borderStyle: ({ borderStyle }) => borderStyle?.lineStyle || 'solid',
      borderRadius: ({ borderStyle }) => borderStyle?.radius || 0,
    },
    container: {
      display: 'flex',
      flexWrap: ({ flexWrap }) => flexWrap,
      flexDirection: ({ flexDirection }) => flexDirection,
      '& > *': {
        marginBottom: theme.spacing(0),
      },
    },
    children: {
      flexGrow: ({ flexGrow }) => flexGrow,
    },
  })
);

export const FlexboxContainerPropertySection = ({
  editingContextId,
  formId,
  widget,
  widgetSubscriptions,
  setSelection,
  readOnly,
}: FlexboxContainerPropertySectionProps) => {
  const classes = useFlexboxContainerPropertySectionStyles({
    flexDirection: widget.flexDirection,
    flexWrap: widget.flexWrap,
    flexGrow: widget.flexGrow,
    borderStyle: widget.borderStyle,
  });

  let children = widget.children.map((widget) => (
    <div className={classes.children} key={widget.id}>
      <PropertySection
        editingContextId={editingContextId}
        formId={formId}
        widget={widget}
        widgetSubscriptions={widgetSubscriptions}
        setSelection={setSelection}
        readOnly={readOnly || widget.readOnly}
        key={widget.id}
      />
    </div>
  ));

  return (
    <div className={classes.containerAndLabel} data-testid={`flexbox-${widget.label}`}>
      <PropertySectionLabel editingContextId={editingContextId} formId={formId} widget={widget} subscribers={[]} />
      <div className={classes.container}>{children}</div>
    </div>
  );
};
